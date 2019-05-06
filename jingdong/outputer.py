# coding=utf-8

import datetime
import pandas as pd
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
        # to csv
       # df = pd.DataFrame(data.items(), columns=['item','content'])
       # bak_file = './result/output_%s.csv'%time_now.strftime('%Y-%m-%d-%H-%M-%S')
       # df.to_csv(bak_file, index=False, header=False, sep='\t', mode='wb', encoding='utf-8')  # for bak_up
       # df.to_csv('./result/output.csv', index=False, header=False, sep='\t', mode='wb', encoding='utf-8')
       # logger.info('Bak_file: %s' %bak_file)
        # to mysql
        sql = mysql.Mysql()
        print table_name
        sql.insert_data(table_name, data)
