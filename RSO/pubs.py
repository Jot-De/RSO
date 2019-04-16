from flask import Flask, request, jsonify, render_template
import os
from flask_restful import Api, Resource
from flask_migrate import Migrate
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
# app.config['SECRET_KEY'] = 'mysecretkey
basedir = os.path.abspath(os.path.dirname(__file__))
app.config['SQLALCHEMY_DATABASE_URI'] = 'postgres://postgres:1234@localhost:5432/postgres'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)
Migrate(app,db)
api = Api(app)


@app.route('/')
def index():
    return render_template('home.html')


class Pubs(db.Model):

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
    
    def city_json(self):
        return {'city' : self.city}

    def json_f(self):
        return {'city':self.city, 'info': self.info, 'name': self.name, 'ID' : self.pub_id}

    def __str__(self):
        return "{} and {} and {} and {}".format(self.pub_id, self.name, self.info, self.city)

class PubData(Resource):

    def get(self,name):
        pub = Pubs.query.filter_by(name=name).first()
        if pub:
            return pub.json_f()
        else:
            return {'name':'not found'}, 404

    def post(self,name):
        pub = Pubs(name=name)
        db.session.add(pub)
        db.session.commit()
        return pub.json_f()

    def delete(self,name):
        pub = Pubs.query.filter_by(name=name).first()
        db.session.delete(pub)
        db.session.commit()

class CityPubGet(Resource):
    
    def get(self,name):
        pub = Pubs.query.filter_by(name=name).first()
        if pub:
            return pub.city_json()
        else:
            return {'name':'not found'}, 404            


class CityPubPut(Resource):

    def put(self,name, city):
        pub = Pubs.query.filter_by(name=name).update(dict(city=city))
        db.session.commit()


class InfoPubGet(Resource):
    
    def get(self,name):
        pub = Pubs.query.filter_by(name=name).first()
        if pub:
            return pub.info_json()
        else:
            return {'name':'not found'}, 404            


class InfoPubPut(Resource):

    def put(self,name, info):
        pub = Pubs.query.filter_by(name=name).update(dict(info=info))
        db.session.commit()


class AllPubs(Resource):

    def get(self):
        pubs = Pubs.query.all()
        return [pub.pub_list_json() for pub in pubs]


api.add_resource(PubData, '/pubs/<string:name>')
api.add_resource(AllPubs,'/pubs')
api.add_resource(InfoPubPut, '/pubs/<string:name>/info/<string:info>')
api.add_resource(InfoPubGet, '/pubs/<string:name>/info')
api.add_resource(CityPubPut, '/pubs/<string:name>/city/<string:city>')
api.add_resource(CityPubGet, '/pubs/<string:name>/city')


if __name__ == '__main__':
    db.create_all()
    app.run(host = '0.0.0.0' ,port = 5001,debug=True)



