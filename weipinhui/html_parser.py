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
        #{"sell_price":299,"market_price":320,"discount":"9.3\u6298","discount_index":9.3,"extraDiscountType":0,"originalPrice":null,"is_from_ps":1}
        #goods_imgre = re.compile('"names":'+u'".{1,9}"')
        prices_imgre = re.compile(r'"sell_price":(\d+)')
        prices= prices_imgre.findall(html)
        for price in prices:
            price=price[0:]
            return  price

    def parse_price(self,page_url,html_cont):#
        if page_url is None or html_cont is None:
            return
        new_price=self._get_new_price(html_cont)
        return new_price


