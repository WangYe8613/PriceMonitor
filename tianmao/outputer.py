# coding=utf-8

import datetime
from abc import ABCMeta, abstractmethod
import logging
import ConfigParser
import mysql

config = ConfigParser.ConfigParser()
config.read('./config/config.ini')
table_name = config.get('mysql', 'table_name')


class Outputer(object):
    __metaclass__ = ABCMeta

    @abstractmethod
    def output(self, data):
        print 1111111111


# 继承Outputer类
class OutputerPrice(Outputer):
    def output(self, data):
        # to mysql
        sql = mysql.Mysql()
        print table_name
        sql.insert_data(table_name, data)
