from reviews import db

class Reviews(db.Model):

    review_id = db.Column(db.Integer,primary_key=True, autoincrement=True)
    pub_id = db.Column(db.Integer)
    opinion = db.Column(db.String(800))
    stars = db.Column(db.String(50))

    def __init__(self,pub_id):
        self.opinion = "jeszcz nie dodano recenzji tego pubu :{"
        self.stars = "brak jeszcze oceny tego pubu :<"
        self.pub_id="chyba coś jest niedokończone, bo ta recenzja nie jest przypisana do żadnego pubu :("

    def review_list_json(self):
        return {'pub_id': self.pub_id, 'ID' : self.review_id}

    def pub_id_json(self):
        return {'pub_id' : self.pub_id}

    def opinion_json(self):
        return {'description' : self.opinion}

    def delete_json(self):
        return {'delete' : self.pub_id}

    def stars_json(self):
        return {'stars' : self.stars}

    def json_f(self):
        return {'stars':self.stars, 'opinion': self.opinion, 'pub_id': self.pub_id, 'ID' : self.review_id}

    def __str__(self):
        return "{} and {} and {} and {}".format(self.review_id, self.pub_id, self.opinion, self.stars)