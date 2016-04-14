#!/usr/bin/python

__author__ = "Chinmaya Gautam"
__copyright__ = "Copyright 2016, The Anaphora Resolution"
__credits__ = ["Chinamya Gautam", "Harsh Fatehpuria", "Rahul Agrawal", "Simrat Singh Chabbra"]
__license__ = "GPL"
__version__ = "1.0.2"
__maintainer__ = "Chinmaya Gautam"
__email__ = "chinmaya.gautam@usc.edu"
__status__ = "Developement"

import mechanize
import cookielib
from bs4 import BeautifulSoup, Tag

#Browser
br = mechanize.Browser()

#Cookie Jar
cj = cookielib.LWPCookieJar()
br.set_cookiejar(cj)

#Browser options
br.set_handle_equiv(True)
br.set_handle_gzip(True)
br.set_handle_redirect(True)
br.set_handle_referer(True)
br.set_handle_robots(False)

# Follows refresh 0 but not hangs on refresh > 0
br.set_handle_refresh(mechanize._http.HTTPRefreshProcessor(), max_time=1)

#User-Agent
br.addheaders = [('User-agent', 'Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.1) Gecko/2008071615 Fedora/3.0.1-1.fc9 Firefox/3.0.1')]

#Open web interface for the shallow parser
r = br.open('http://ltrc.iiit.ac.in/analyzer/hindi/')
html = r.read()

#Select the first (index zero form)
br. select_form(nr=0)

infile = open("infile.txt")
data = infile.read()
infile.close()

#Let's search
br.form['input'] = data
br.submit()

html = br.response().read()
soup = BeautifulSoup(html, "html.parser")
res_table = soup.find_all('table')[1]

res = list()
for entry in res_table.find_all('td'):
     res.append(entry.get_text().encode('utf-8'))

outfile = open('parsed_data.txt', 'w')

#all done! write the parsed data!
for r in res:
    outfile.write(r + "\n")

outfile.close()
