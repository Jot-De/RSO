from pubs import db

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