from pubs import db

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
    pub_photo = db.Column(db.BLOB)
 
    def __init__(self,pub_id,pub_photo):
        self.pub_id   = pub_id
        self.pub_photo = pub_photo

    def json_f(self):
        return {'pub_id':self.pub_id, 'id_photo':self.pub_photo}
