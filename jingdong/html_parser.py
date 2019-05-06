#!/usr/bin/python
#coding:utf-8

import urllib2
import json
class HtmlParser(object):
    def _get_new_price(self,url):
        print url
        sku = url.split('/')[-1].strip(".html")
        print sku
        price_url = "https://pe.3.cn/prices/mgets?sources=wxsq&skuIds=" + sku
        print price_url
        response = urllib2.urlopen(price_url)
        content = response.read()

        result = json.loads(content)
        print result
        record = result[0]
        print 'price:',record['p']
        return record['p']

    def parse_price(self,page_url):#
        if page_url is None:
            return
        new_price=self._get_new_price(page_url)
        return new_price


