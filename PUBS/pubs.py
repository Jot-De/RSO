from flask import Flask, request, jsonify, render_template
import werkzeug, os
from flask_restful import Api, Resource, reqparse
from flask_migrate import Migrate
from flask_sqlalchemy import SQLAlchemy
import psycopg2
import base64
import json 

app = Flask(__name__)
# app.config['SECRET_KEY'] = 'mysecretkey'
basedir = os.path.abspath(os.path.dirname(__file__))

app.config['SQLALCHEMY_DATABASE_URI'] = 'postgres://postgres:1234@localhost:5432/pubssql'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)

UPLOAD_FOLDER = 'static/img'

Migrate(app,db)
api = Api(app)

from models import *

@app.route('/')
def index():
    
    return render_template('home.html')

# Parsers to json
parser = reqparse.RequestParser()
parser.add_argument('tag_desc', type=str)
parser.add_argument('name', type=str)
parser.add_argument('city', type=str)
parser.add_argument('info', type=str)
parser.add_argument('file',type=werkzeug.datastructures.FileStorage, location='files')

# get pubs specific info, also to delete pub '/pubs/<int:pub_id>'
class PubData(Resource): 

    def get(self,pub_id):

        args = parser.parse_args()
        pub = pub_table.query.filter_by(pub_id=pub_id).first()
        tags = PubMapTag.query.filter_by(pub_id=pub_id).all()
        if pub:
            # return pub.json_f()
            
            return {'name':pub.name, 'tags':[tag.json_f() for tag in tags],'city':pub.city,'pub_id':pub.pub_id,'info':pub.info}
        else:
            
            return {'id':'not found'}, 404

    def delete(self,pub_id):

        pub = pub_table.query.filter_by(pub_id=pub_id).first()
        if pub:
            db.session.delete(pub)
            db.session.commit()
           
            return pub.delete_json()
        else:
           
            return {'id':'not found'}, 404  


# you can add pub by giving it's name '/pubs'
class AddPubs(Resource): 
    
    def post(self):

        args = parser.parse_args() #add parsing regquest functionality , shown line below
        pub = pub_table(name=args['name']) 
        db.session.add(pub)
        db.session.commit()

        return pub.name_json()

#get info about city in which pub exists '/pubs/<int:pub_id>/city'
class CityPubGet(Resource):
    
    def get(self,pub_id):

        pub = pub_table.query.filter_by(pub_id=pub_id).first()
        if pub:

            return pub.city_json()
        else:

            return {'id':'not found'}, 404            

# put info about pub's city'/pubs/<d>/city'
class CityPubPut(Resource): 

    def patch(self,pub_id):

        pub = pub_table.query.filter_by(pub_id=pub_id).first()
        if pub:
            args = parser.parse_args() #add parsing regquest functionality , shown line below
            pub = pub_table.query.filter_by(pub_id=pub_id).update(dict(city=args['city']))
            db.session.commit()
            pub = pub_table.query.filter_by(pub_id=pub_id).first()
           
            return pub.city_json()
        else:
           
            return {'id':'not found'}, 404  

# get some info about pub '/pubs/<int:pub_id>/info'
class InfoPubGet(Resource): 
    
    def get(self,pub_id):

        pub = pub_table.query.filter_by(pub_id=pub_id).first()
        if pub:
      
            return pub.info_json()
        else:
      
            return {'id':'not found'}, 404            

#insert short info about pub '/pubs/<int:pub_id>/info'
class InfoPubPut(Resource): 
    
    def patch(self,pub_id):

        pub = pub_table.query.filter_by(pub_id=pub_id).first()
        if pub:
            args = parser.parse_args() #add parsing regquest functionality , shown line below
            pub = pub_table.query.filter_by(pub_id=pub_id).update(dict(info=args['info']))
            db.session.commit()
            pub = pub_table.query.filter_by(pub_id=pub_id).first()
           
            return pub.info_json()
        else:
           
            return {'id':'not found'}, 404  

#show all pubs '/pubs'
class AllPubs(Resource): 
   
    def get(self):

        pubs = pub_table.query.all()
        return [pub.pub_list_json() for pub in pubs]

#add tag to the specified pub
class TagPubPut(Resource):

    def post(self,pub_id):

        pub = pub_table.query.filter_by(pub_id=pub_id).first()
        if pub:
            args = parser.parse_args()
            tag = PubMapTag(pub_id=pub_id, tag_desc=args['tag_desc'])
            desc = args['tag_desc']
            if desc:
                db.session.add(tag)
                db.session.commit()
                tag = PubMapTag.query.filter_by(pub_id=pub_id).first()
                tag = PubMapTag.query.filter_by(pub_id=pub_id).order_by(db.desc('map_id')).first()
            
                return tag.json_f()
            else:
            
                return {'tag':'empty'}, 404 

        else:
            
            return {'id':'not found'}, 404 

# get some info about pub '/pubs/<int:pub_id>/info'
class TagGet(Resource): 
    
    def get(self,pub_id):

        pub = pub_table.query.filter_by(pub_id=pub_id).first()
        tags = PubMapTag.query.filter_by(pub_id=pub_id).all()
        if pub:
          
            return [tag.json_f() for tag in tags]
        else:
           
            return {'id':'not found'}, 404     
  
#upload photo to the pub
class PhotoUpload(Resource):

    def post(self,pub_id):
     
        pub = pub_table.query.filter_by(pub_id=pub_id).first()
        data = parser.parse_args()
        if data['file'] == "":
           
            return {
                    'error':'No file'
                   }
        photo = data['file']
        bin_photo = base64.b64encode(photo.read())

        if photo:
            filename = 'your_image.png'
            photo.save(os.path.join(UPLOAD_FOLDER,filename))
            new_photo = PubPhoto(pub_id = pub_id, pub_photo = bin_photo)
            db.session.add(new_photo)
            db.session.commit()

            return {
                    'message':'photo uploaded'
                    }
        
        return {

                'error':'bad upload'
                }

#return photo from the pub
class GetPhoto(Resource):
    
    def get(self,photo_id):
       
        photo = PubPhoto.query.filter_by(photo_id=photo_id).first()
        if photo:
       
            return photo.json_f()
        else:
         
            return {'id':'not found'}, 404  


#return all pubs in the city
class Cities(Resource):
  
    def get(self):
      
        args = parser.parse_args()
        pubs = pub_table.query.filter_by(city=args['city']).all()
        if pubs:
         
            return [pub.pub_list_json() for pub in pubs]
        else:
        
            return {'city':'not found'}, 404     
  

api.add_resource(GetPhoto,'/pubs/<int:photo_id>/photo')
api.add_resource(PhotoUpload,'/pubs/<int:pub_id>/upload')

api.add_resource(AllPubs,'/pubs')
api.add_resource(AddPubs,'/pubs')

api.add_resource(PubData, '/pubs/<int:pub_id>')

api.add_resource(CityPubPut, '/pubs/<int:pub_id>/city')
api.add_resource(CityPubGet, '/pubs/<int:pub_id>/city')
api.add_resource(Cities, '/pubs/city')


api.add_resource(InfoPubPut, '/pubs/<int:pub_id>/info')
api.add_resource(InfoPubGet, '/pubs/<int:pub_id>/info')

api.add_resource(TagPubPut, '/pubs/<int:pub_id>/tag')
api.add_resource(TagGet, '/pubs/<int:pub_id>/tag')


if __name__ == '__main__':
    db.create_all()
    app.run(host = '127.0.0.1' ,port = 5003,debug=True) # ssl_context='adhoc' -> https