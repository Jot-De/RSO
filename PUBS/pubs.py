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


class pub_table(db.Model):

    pub_id = db.Column(db.Integer,primary_key=True, autoincrement=True) 
    name = db.Column(db.String(80))
    info = db.Column(db.String(800))
    city = db.Column(db.String(50))


    def __init__(self,name):
        self.name=name
        self.info = "jeszcz nie dodano informacji o pubie"
        self.city = "brak informacji o miescie"

    def pub_list_json(self):
        return {'name': self.name, 'ID' : self.pub_id}

    def info_json(self):
        return {'description' : self.info}
    
    def name_json(self):
        return {'name' : self.name}
    
    def delete_json(self):
        return {'delete' : self.name}

    def city_json(self):
        return {'city' : self.city}

    def json_f(self):
        return {'city':self.city, 'info': self.info, 'name': self.name, 'ID' : self.pub_id}

    def __str__(self):
        return "{} and {} and {} and {}".format(self.pub_id, self.name, self.info, self.city)


class PubMapTag(db.Model):
    map_id = db.Column(db.Integer,primary_key=True, autoincrement=True)
    pub_id = db.Column(db.Integer,db.ForeignKey("pub_table.pub_id")) 
    tag_desc = db.Column(db.String(30))
 
    def __init__(self,pub_id,tag_desc):
        self.pub_id   = pub_id
        self.tag_desc = tag_desc

    def json_f(self):
        return {'tag_id':self.map_id, 'tag_desc':self.tag_desc}

class PubPhoto(db.Model):
    photo_id = db.Column(db.Integer,primary_key=True, autoincrement=True)
    pub_id = db.Column(db.Integer,db.ForeignKey("pub_table.pub_id")) 
    pub_photo = db.Column(db.LargeBinary)
    
    def __init__(self,pub_id,pub_photo):
        self.pub_id   = pub_id
        self.pub_photo = pub_photo

    def json_f(self):
        self.napiss = str(self.pub_photo)
        self.napisss = self.napiss[2:]
        self.napissss = self.napisss[:-1]
        return {'pub_id':self.pub_id, 'id_photo':self.photo_id, 'image':self.napissss}

@app.route('/')
def index():
    return render_template('home.html')


parser = reqparse.RequestParser()
parser.add_argument('tag_desc', type=str)
parser.add_argument('name', type=str)
parser.add_argument('city', type=str)
parser.add_argument('info', type=str)
parser.add_argument('file',type=werkzeug.datastructures.FileStorage, location='files')

class PubData(Resource): # get pubs specific info, also to delete pub '/pubs/<int:pub_id>'

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
            
class AddPubs(Resource): # you can add pub by giving it's name '/pubs'
    
    def post(self):

        args = parser.parse_args() #add parsing regquest functionality , shown line below
        pub = pub_table(name=args['name']) 
        db.session.add(pub)
        db.session.commit()
        return pub.name_json()

class CityPubGet(Resource): #get info about city in which pub exists '/pubs/<int:pub_id>/city'
    
    def get(self,pub_id):

        pub = pub_table.query.filter_by(pub_id=pub_id).first()
        if pub:
            return pub.city_json()
        else:
            return {'id':'not found'}, 404            
class CityPubPut(Resource): # put info about pub's city'/pubs/<d>/city'

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
            
class InfoPubGet(Resource): # get some info about pub '/pubs/<int:pub_id>/info'
    
    def get(self,pub_id):

        pub = pub_table.query.filter_by(pub_id=pub_id).first()
        if pub:
            return pub.info_json()
        else:
            return {'id':'not found'}, 404            

class InfoPubPut(Resource): #insert short info about pub '/pubs/<int:pub_id>/info'
    
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
class AllPubs(Resource): #show all pubs '/pubs'
   
    def get(self):

        pubs = pub_table.query.all()
        return [pub.pub_list_json() for pub in pubs]

class TagPubPut(Resource):

    def post(self,pub_id):

        pub = pub_table.query.filter_by(pub_id=pub_id).first()
        if pub:
            args = parser.parse_args()
            tag = PubMapTag(pub_id=pub_id, tag_desc=args['tag_desc'])
            db.session.add(tag)
            db.session.commit()
            tag = PubMapTag.query.filter_by(pub_id=pub_id).first()
            tag = PubMapTag.query.filter_by(pub_id=pub_id).order_by(db.desc('map_id')).first()
            return tag.json_f()
        else:
            return {'id':'not found'}, 404 


class TagGet(Resource): # get some info about pub '/pubs/<int:pub_id>/info'
    
    def get(self,pub_id):

        pub = pub_table.query.filter_by(pub_id=pub_id).first()
        tags = PubMapTag.query.filter_by(pub_id=pub_id).all()
        if pub:
            return [tag.json_f() for tag in tags]
        else:
            return {'id':'not found'}, 404     
  

class PhotoUpload(Resource):

    def post(self,pub_id):
        pub = pub_table.query.filter_by(pub_id=pub_id).first()
        data = parser.parse_args()
        if data['file'] == "":
            return {
                    'message':'No file found',
                    'status':'error'
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
                    'message':'photo uploaded',
                    'status':'success'
                    }
        return {

                'message':'Something when wrong',
                'status':'error'
                }

class GetPhoto(Resource):
    def get(self,photo_id):
        photo = PubPhoto.query.filter_by(photo_id=photo_id).first()
        if photo:
            return photo.json_f()
        else:
            return {'id':'not found'}, 404  

api.add_resource(GetPhoto,'/pubs/<int:photo_id>/photo')
api.add_resource(PhotoUpload,'/pubs/<int:pub_id>/upload')

api.add_resource(AllPubs,'/pubs')
api.add_resource(AddPubs,'/pubs')

api.add_resource(PubData, '/pubs/<int:pub_id>')

api.add_resource(CityPubPut, '/pubs/<int:pub_id>/city')
api.add_resource(CityPubGet, '/pubs/<int:pub_id>/city')

api.add_resource(InfoPubPut, '/pubs/<int:pub_id>/info')
api.add_resource(InfoPubGet, '/pubs/<int:pub_id>/info')

api.add_resource(TagPubPut, '/pubs/<int:pub_id>/tag')
api.add_resource(TagGet, '/pubs/<int:pub_id>/tag')


if __name__ == '__main__':
    db.create_all()
    app.run(host = '127.0.0.1' ,port = 5003,debug=True) # ssl_context='adhoc' -> https