from flask import Flask, request, jsonify, render_template
import os
from flask_restful import Api, Resource, reqparse
from flask_migrate import Migrate
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
# app.config['SECRET_KEY'] = 'mysecretkey
basedir = os.path.abspath(os.path.dirname(__file__))
app.config['SQLALCHEMY_DATABASE_URI'] = 'postgres://postgres:1234@localhost:5432/pubs'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)
Migrate(app,db)
api = Api(app)


@app.route('/')
def index():
    return render_template('home.html')

parser = reqparse.RequestParser()
parser.add_argument('name', type=str)
parser.add_argument('city', type=str)
parser.add_argument('info', type=str)


class Pubsy(db.Model):

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

    def city_json(self):
        return {'city' : self.city}

    def json_f(self):
        return {'city':self.city, 'info': self.info, 'name': self.name, 'ID' : self.pub_id}

    def __str__(self):
        return "{} and {} and {} and {}".format(self.pub_id, self.name, self.info, self.city)

class PubData(Resource): # get pubs specific info, also to delete pub '/pubs/<string:name>'

    def get(self,name):
        pub = Pubsy.query.filter_by(name=name).first()
        if pub:
            return pub.json_f()
        else:
            return {'name':'not found'}, 404

    def delete(self,name):
        pub = Pubsy.query.filter_by(name=name).first()
        db.session.delete(pub)
        db.session.commit()

class AddPubs(Resource): # you can add pub by giving it's name '/pubs'
    def post(self):
        args = parser.parse_args() #add parsing regquest functionality , shown line below
        pub = Pubsy(name=args['name']) 
        db.session.add(pub)
        db.session.commit()
        return pub.name_json()

class CityPubGet(Resource): #get info about city in which pub exists '/pubs/<string:name>/city'
    
    def get(self,name):
        pub = Pubsy.query.filter_by(name=name).first()
        if pub:
            return pub.city_json()
        else:
            return {'name':'not found'}, 404            

class CityPubPut(Resource): # put info about pub's city'/pubs/<string:name>/city'

    def put(self,name):
        args = parser.parse_args() #add parsing regquest functionality , shown line below
        pub = Pubsy.query.filter_by(name=name).update(dict(city=args['city']))
        db.session.commit()
        pub = Pubsy.query.filter_by(name=name).first()
        return pub.city_json()

class InfoPubGet(Resource): # get some info about pub '/pubs/<string:name>/info'
    
    def get(self,name):
        pub = Pubsy.query.filter_by(name=name).first()
        if pub:
            return pub.info_json()
        else:
            return {'name':'not found'}, 404            

class InfoPubPut(Resource): #insert short info about pub '/pubs/<string:name>/info'

    def put(self,name):
        args = parser.parse_args()
        pub = Pubsy.query.filter_by(name=name).update(dict(info=args['info']))
        db.session.commit()
        pub = Pubsy.query.filter_by(name=name).first()
        return pub.info_json()

class AllPubs(Resource): #show all pubs '/pubs'
    def get(self):
        pubs = Pubsy.query.all()
        return [pub.pub_list_json() for pub in pubs]


api.add_resource(AllPubs,'/pubs')
api.add_resource(AddPubs,'/pubs')
api.add_resource(PubData, '/pubs/<string:name>')

api.add_resource(CityPubPut, '/pubs/<string:name>/city')
api.add_resource(CityPubGet, '/pubs/<string:name>/city')


api.add_resource(InfoPubPut, '/pubs/<string:name>/info')
api.add_resource(InfoPubGet, '/pubs/<string:name>/info')


if __name__ == '__main__':
    db.create_all()
    app.run(host = '0.0.0.0' ,port = 5001,debug=True)


#TODO: rozbic na model, dodac komentarze w sumie chyba tyle


##### ************************************** ########

# krótki opis uzycia:
# z poziomu terminala:
# curl http://{adres}/pubs/amplitron/city -d "city=bydgoszcz" -X PUT -v   // dodanie miasta
# curl http://{adres}/pubs -d "name=kofeina2" -X POST -v             // dodanie pubu
# curl http://{adres}/pubs/amplitron/info -d "info=informacja" -X PUT -v //dodanie info

##### ************************************** ########