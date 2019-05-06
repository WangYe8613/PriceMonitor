#!/usr/bin/python
#coding:utf-8

import ConfigParser
import url_manager
import html_downloader
import html_parser
import requests
import outputer

config = ConfigParser.ConfigParser()
config.read('./config/config.ini')
root_url2 = config.get('root_html', 'login_url')

class SpiderMain(object):#爬虫类
    def __init__(self):#初始化函数
        self.urls=url_manager.UrlManager()#url管理器
        self.downloader=html_downloader.HtmlDownloader()#网页下载器
        self.parser=html_parser.HtmlParser()#网页解析器
        self.outputer=outputer.OutputerPrice()

    def craw_price(self,root_url):#爬虫调度器（按步骤调用各个函数）
        print root_url
        try:#抛出异常
            html_cont=self.downloader.login_download('18392852016','zhang0109',root_url) #下载该url网页中的html代码
            new_price=self.parser.parse_price(root_url,html_cont) #解析html代码
            self.outputer.output(new_price)
        except Exception, e:
            print e
            print "craw failed"
        print new_price


if __name__=="__main__":
    obj_spider=SpiderMain()
    obj_spider.craw_price(root_url2)
