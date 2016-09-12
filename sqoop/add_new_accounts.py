#!/usr/bin/python

import peewee
from peewee import *
import datetime
import os
import random
from common import *
import uuid

print "Adding latest account data at " + datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

createBeginDate = datetime.datetime(2014, 3, 14)
createEndDate = datetime.datetime(2014, 3, 15)

lastId = Account.select(fn.Max(Account.acct_num).alias("last_acct_num")).limit(1)[0].last_acct_num
lastId += 1

account = Account(acct_num=lastId, acct_create_dt=random_date(createBeginDate, createEndDate), first_name="Jesse", last_name="Anderson", address="123 Elm St", 
	city="Reno", state="NV", zipcode="89511", phone_number="775-555-1212")
account.save(force_insert=True)

lastId += 1

account = Account(acct_num=lastId, acct_create_dt=random_date(createBeginDate, createEndDate), first_name="Tom", last_name="Wheeler", address="125 Elm St", 
	city="Palo Alto", state="CA", zipcode="94301", phone_number="650-555-1515")
account.save(force_insert=True)

lastId += 1

account = Account(acct_num=lastId, acct_create_dt=random_date(createBeginDate, createEndDate), first_name="Ian", last_name="Wrigley", address="127 Elm St", 
	city="Palo Alto", state="CA", zipcode="94301", phone_number="650-555-1777")
account.save(force_insert=True)

print "Finished Adding Accounts"