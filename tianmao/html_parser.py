#!/usr/bin/python
#coding:utf-8

import urllib
from bs4 import BeautifulSoup
import re
import os
import time
import codecs
from lxml import etree

class HtmlParser(object):
    def _get_new_price(self,html):
        #{"names":"页岩灰 官方标配 ","pvs":"1627207:789488376;5919063:6536025","skuId":"3473513664832"},
        #{"priceCent":119800,"price":"1198.00","cspuId":1000032169140842,"stock":99,"skuId":"3473513664832"}
        #<meta name="keywords" content="阿玛尼纯净持妆粉底液正品LSUV 持久控油不脱妆 油皮必备"/>
        #goods_imgre = re.compile(r'"names":'+u'".{1,9}"')
        prices_imgre = re.compile(r'"price":"\d+.\d+"')
        # 匹配出所有的图片链接，返回结果为url列表
        #goods= goods_imgre.findall(html)
        #for good in goods:
            #good=good[9:]
            #good=good[:-1]
            #print good
        prices= prices_imgre.findall(html)
        for price in prices:
            price=price[9:]
            return price[:-1]

    def parse_price(self,page_url,html_cont):#
        if page_url is None or html_cont is None:
            return
        new_price=self._get_new_price(html_cont)
        return new_price


