FROM python:3.7-alpine

RUN apk update && apk add postgresql-dev gcc python3-dev musl-dev 
RUN pip install flask flask-migrate flask-SQLAlchemy flask-WTF flask-login flask-restful Psycopg2

RUN pip install flask

ADD . /app

WORKDIR /app

CMD ["python", "pubs.py"]