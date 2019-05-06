#!/usr/bin/python
#coding:utf-8

import urllib2
import requests
import ConfigParser

config = ConfigParser.ConfigParser()
config.read('./config/config.ini')

good_url = config.get('root_html', 'goods_url')
class HtmlDownloader(object):
    def login_download(self,name,password,url):
        print url
        session = requests.session()
        login_data = {
            'userName': name,
            'passWord': password,
            'enter': 'true'
        }
        session.post(url, data=login_data)
        res = session.get(good_url)
        return (res.text)

    def download(self,url):
        if url is None:
            return None
        #伪装成浏览器去爬取网页html源码，反爬机制1
        headers={'Accept': '*/*',
                 'Accept-Language': 'en-US,en;q=0.8',
                 'Cache-Control': 'max-age=0',
                 'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36',
                 'Connection': 'keep-alive',
                 'Referer': 'http://www.baidu.com/'
                 }
        print 2222222222222
        req = urllib2.Request(url,None,headers)
        response=urllib2.urlopen(req)#打开url网页
        if response.getcode()!=200:#判断是否打开成功
            return None
        return response.read()
