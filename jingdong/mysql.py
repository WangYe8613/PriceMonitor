#!/usr/bin/python
#!coding=utf-8

import datetime
import pymysql
import pandas as pd
import ConfigParser

config = ConfigParser.ConfigParser()
config.read('./config/config.ini')

user = config.get('mysql', 'user')
passwd = config.get('mysql', 'passwd')
database = config.get('mysql', 'database')
good_orig = float(config.get('mysql', 'conmpany'))
url_name = config.get('mysql','name')
default = config.get('mysql','other')

class Mysql(object):
    def __init__(self):
        print user,passwd,database
        self.db = pymysql.connect("localhost", user, passwd, database)
        self.cursor = self.db.cursor()
        print database
    def insert_data(self, table_name, data):
        tmp_string = data.encode("utf-8")
        tmp_value = float(tmp_string)
        time_now = datetime.datetime.now()
        tmp_time ='\"' + time_now.strftime('%Y-%m-%d %H:%M:%S') + '\"'
        print type(url_name),type(good_orig),tmp_value,tmp_time,default
        sql = 'insert into %s values (%s,%d,%f,%s,%s)' %(table_name,url_name,good_orig,tmp_value,tmp_time,default)
        try:
            self.cursor.execute(sql)
            self.db.commit()
            print 'Sucessfully insert data into table'
        except Exception, e:
            print e
            print "faliure"
            self.db.rollback()
            return

    def __del__(self):
        self.db.close()

