from flask import Flask, request, jsonify, render_template
import os
from flask_restful import Api, Resource, reqparse
from flask_migrate import Migrate
from flask_sqlalchemy import SQLAlchemy


app = Flask(__name__)
# app.config['SECRET_KEY'] = 'mysecretkey'
basedir = os.path.abspath(os.path.dirname(__file__))
app.config['SQLALCHEMY_DATABASE_URI'] = 'postgres://postgres:1234@localhost:5432/postgres'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)
Migrate(app,db)
api = Api(app)

from models import * #don't know how but only that way it works

@app.route('/')
def index():
    return render_template('home.html')

parser = reqparse.RequestParser()
parser.add_argument('tag_desc', type=str)
parser.add_argument('name', type=str)
parser.add_argument('city', type=str)
parser.add_argument('info', type=str)

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
    app.run(host = '127.0.0.1' ,port = 5003,debug=True)  # ssl_context='adhoc' -> https
