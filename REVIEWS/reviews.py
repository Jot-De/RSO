from flask import Flask, request, jsonify, render_template
import os
from flask_restful import Api, Resource, reqparse
from flask_migrate import Migrate
from flask_sqlalchemy import SQLAlchemy


app = Flask(__name__)
# app.config['SECRET_KEY'] = 'mysecretkey'
basedir = os.path.abspath(os.path.dirname(__file__))
app.config['SQLALCHEMY_DATABASE_URI'] = 'postgres://postgres:1234@localhost:5432/reviews'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)
Migrate(app,db)
api = Api(app)

from models import * #don't know how but only that way it works

@app.route('/')
def index():
    return render_template('home.html')


parser = reqparse.RequestParser()
parser.add_argument('pub_id', type=int)
parser.add_argument('stars', type=str)
parser.add_argument('opinion', type=str)

class ReviewData(Resource): #to get the text of a review and to delate a review '/reviews/<int:review_id>'

    def get(self,review_id):
        review = Reviews.query.filter_by(review_id=review_id).first()
        if review:
            return review.json_f()
        else:
            return {'id':'not found'}, 404

    def delete(self,review_id):
        review = Reviews.query.filter_by(review_id=review_id).first()
        if review:
            db.session.delete(review)
            db.session.commit()
            return review.delete_json()
        else:
            return {'id':'not found'}, 404   

            
class AddReviews(Resource): # you can add review by giving it's pub_id '/reviews'
    def post(self):
        args = parser.parse_args() #add parsing regquest functionality , shown line below
        review = Reviews(pub_id=args['pub_id'])
        db.session.add(review)
        db.session.commit()
        return review.pub_id_json()

###################Wiem, że się nie daje tego z dupy, tylko recenzja jest na pub ale to wersja alfa############
class PubIDReviewGet(Resource): #get pub_id  for a review '/reviews/<int:review_id>/pub_id'

    def get(self,review_id):
        review = Reviews.query.filter_by(review_id=review_id).first()
        if review:
            return review.pub_id_json()
        else:
            return {'id':'not found'}, 404

class PubIDReviewPut(Resource): # put pub_id for a review '/reviews/<d>/pub_id'

    def put(self,review_id):
        args = parser.parse_args() #add parsing regquest functionality , shown line below
        review = Reviews.query.filter_by(review_id=review_id).update(dict(pub_id=args['pub_id']))
        db.session.commit()
        review = Reviews.query.filter_by(review_id=review_id).first()
        return review.pub_ids_json()

############################################################################################################

class StarsReviewGet(Resource): #get stars  for a review '/reviews/<int:review_id>/stars'

    def get(self,review_id):
        review = Reviews.query.filter_by(review_id=review_id).first()
        if review:
            return review.stars_json()
        else:
            return {'id':'not found'}, 404

class StarsReviewPut(Resource): # put stars for a review '/reviews/<d>/stars'

    def put(self,review_id):
        args = parser.parse_args() #add parsing regquest functionality , shown line below
        review = Reviews.query.filter_by(review_id=review_id).update(dict(stars=args['stars']))
        db.session.commit()
        review = Reviews.query.filter_by(review_id=review_id).first()
        return review.stars_json()

class OpinionReviewGet(Resource): # get the opinion  for a review '/reviews/<int:review_id>/opinion'
    
    def get(self,review_id):
        review = Reviews.query.filter_by(review_id=review_id).first()
        if review:
            return review.opinion_json()
        else:
            return {'id':'not found'}, 404            

class OpinionReviewPut(Resource): #insert short opinion about review '/reviews/<int:review_id>/opinion'

    def put(self,review_id):
        args = parser.parse_args()
        review = Reviews.query.filter_by(review_id=review_id).update(dict(opinion=args['opinion']))
        db.session.commit()
        review = Reviews.query.filter_by(review_id=review_id).first()
        return review.opinion_json()

class AllReviews(Resource): #show all reviews '/reviews'
    def get(self):
        reviews = Reviews.query.all()
        return [review.review_list_json() for review in reviews]




api.add_resource(AllReviews,'/reviews')
api.add_resource(AddReviews,'/reviews')
api.add_resource(ReviewData, '/reviews/<int:review_id>')

api.add_resource(PubIDReviewPut, '/reviews/<int:review_id>/pub_id')
api.add_resource(PubIDReviewGet, '/reviews/<int:review_id>/pub_id')

api.add_resource(StarsReviewPut, '/reviews/<int:review_id>/stars')
api.add_resource(StarsReviewGet, '/reviews/<int:review_id>/stars')


api.add_resource(OpinionReviewPut, '/reviews/<int:review_id>/opinion')
api.add_resource(OpinionReviewGet, '/reviews/<int:review_id>/opinion')


if __name__ == '__main__':
    db.create_all()
    app.run(host = '127.0.0.1' ,port = 5003,debug=True)  # ssl_context='adhoc' -> https

