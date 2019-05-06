#!/usr/bin/python
#!coding:utf-8

import ConfigParser
import os

curPath = os.path.dirname(os.path.realpath(__file__))
curPath += '/config/'
cfgPath = os.path.join(curPath, "config.ini")

print cfgPath

conf = ConfigParser.ConfigParser()
conf.read(cfgPath)


#从数据库里面读取商品url和商品名称
url = '商品url'
name = '商品名称'
conf.set("root_html","goods_url",url)
conf.set("mysql","name",name)


conf.write(open(cfgPath,"r+"))
