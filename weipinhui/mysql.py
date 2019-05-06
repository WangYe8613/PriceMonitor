# coding=utf-8

import datetime
import pymysql
import pandas as pd
import ConfigParser

config = ConfigParser.ConfigParser()
config.read('./config/config.ini')

user = config.get('mysql', 'user')
passwd = config.get('mysql', 'passwd')
database = config.get('mysql', 'database')
good_orig = int(config.get('mysql', 'conmpany'))
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

#    def create_table(self, table_name, keys, data_types):
#        if len(keys) != len(data_types):
#            logger.error('length of KEYS is not equal to length of DATA_TYPES.')
#            return
#
#        pair = zip(keys, data_types)
#        tmp = []
#        for item in pair:
#            tmp.append('%s %s'%(item[0], item[1]))
#        tmp = str(tuple(tmp))
#
#        sql = 'create table if not exists %s %s'%(table_name, tmp)
#        try:
#            self.cursor.execute(sql)
#            logger.info('Sucessfully create table(%s)!'%table_name)
#        except Exception,e:
#            logger.error(e)
#            logger.error('Fail to create table(%s)'%table_name)
#            return
#

    # def drop_table(self, table_name):
    #     sql = 'drop table if exists %s'%table_name
    #     try:
    #         self.cursor.execute(sql)
    #         logger.info('Sucessfully drop table(%s).'%table_name)
    #     except Exception,e:
    #         logger.error(e)
    #         logger.error('Fail to drop table(%s).'%table_name)
    #         return
