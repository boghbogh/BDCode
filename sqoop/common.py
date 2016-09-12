#!/usr/bin/python

import datetime
import random

import peewee
from peewee import *
import Queue

UPDATES_PER_SECOND = 42

db = MySQLDatabase('loudacre', user='root')

class CustomModel(Model):
    class Meta:
		database = db

class Device(CustomModel):
	device_num = PrimaryKeyField()
	release_dt = DateTimeField()
	device_name = CharField()
	device_type = CharField()

class Account(CustomModel):
	acct_num = IntegerField(primary_key=True)
	acct_create_dt = DateTimeField()
	acct_close_dt = DateTimeField(null=True)
	first_name = CharField()
	last_name = CharField()
	address = CharField()
	city = CharField()
	state = CharField()
	zipcode = CharField()
	phone_number = CharField()
	created = DateTimeField(default=datetime.datetime.now)
	modified = DateTimeField()

	def save(self, *args, **kwargs):
		self.modified = datetime.datetime.now()
		return super(Account, self).save(*args, **kwargs)

	class Meta:
		db_table = "accounts"

class AccountDevice(CustomModel):
	account = ForeignKeyField(Account)
	device = ForeignKeyField(Device)
	activation_date = DateTimeField()
	account_device_id = CharField()

class KnowledgeBase(CustomModel):
	kb_num = PrimaryKeyField()
	kb_docid = CharField()
	kb_content = TextField()
	device = ForeignKeyField(Device)
	associated_files = CharField()
	article_type = CharField()

class CustomerServiceRep(CustomModel):
	acct_num = PrimaryKeyField()
	cs_name = CharField()

class WebPage(CustomModel):
	web_page_num = PrimaryKeyField()
	web_page_file_name = CharField()
	associated_files = CharField()

class WebsiteHit(CustomModel):
	hit_num = PrimaryKeyField()
	hit_time = DateTimeField()
	web_page = ForeignKeyField(WebPage,null=True)
	knowledge_base = ForeignKeyField(KnowledgeBase,null=True)
	customer_service_rep = ForeignKeyField(CustomerServiceRep,null=True)
	account = ForeignKeyField(Account,null=True)
	device = ForeignKeyField(Device,null=True)

class BaseStation(CustomModel):
	station_num = IntegerField(primary_key=True)
	zipcode = CharField()
	city = CharField()
	state = CharField()
	latitude = FloatField()
	longitude = FloatField()

class MostActiveStation(CustomModel):
	most_active_station_num = PrimaryKeyField()
	station_num = IntegerField()
	activity = IntegerField()
	activity_time = CharField()

class CallLog(CustomModel):
	call_num = CharField(primary_key=True)
	start_time = DateTimeField()
	end_time = DateTimeField()
	status = CharField()
	from_phone_number = CharField()
	to_phone_number = CharField()

def random_date(start, end):
    """
    This function will return a random datetime between two datetime 
    objects.
    """
    delta = end - start
    int_delta = (delta.days * 24 * 60 * 60) + delta.seconds
    random_second = random.randrange(int_delta)
    return start + datetime.timedelta(seconds=random_second)

# From http://eli.thegreenplace.net/2010/01/22/weighted-random-generation-in-python/
def weighted_choice(weights):
    totals = []
    running_total = 0

    for w in weights:
        running_total += w
        totals.append(running_total)

    rnd = random.random() * running_total
    for i, total in enumerate(totals):
        if rnd < total:
            return i

# Key = Phone name
# Then:
# Release Date
# Type
phonenames = {
	'Sorrento F00L' : ['2008-10-21', 'phone'],
	'Sorrento F01L' : ['2009-03-11', 'phone'],
	'Sorrento F10L' : ['2009-10-25', 'phone'],
	'Sorrento F11L' : ['2010-04-10', 'phone'],
	'Sorrento F20L' : ['2010-11-01', 'phone'],
	'Sorrento F21L' : ['2011-02-28', 'phone'],
	'Sorrento F22L' : ['2011-10-01', 'phone'],
	'Sorrento F23L' : ['2011-11-21', 'phone'],
	'Sorrento F24L' : ['2012-03-12', 'phone'],
	'Sorrento F30L' : ['2012-06-21', 'phone'],
	'Sorrento F31L' : ['2012-07-04', 'phone'],
	'Sorrento F32L' : ['2012-10-21', 'phone'],
	'Sorrento F33L' : ['2013-03-11', 'phone'],
	'Sorrento F40L' : ['2013-06-26', 'phone'],
	'Sorrento F41L' : ['2013-11-01', 'phone'],
	
	'iFruit 1' : ['2008-10-21', 'phone'],
	'iFruit 2' : ['2010-05-20', 'phone'],
	'iFruit 3' : ['2011-11-02', 'phone'],
	'iFruit 3A' : ['2012-07-21', 'phone'],
	'iFruit 4' : ['2012-10-25', 'phone'],
	'iFruit 4A' : ['2013-02-11', 'phone'],
	'iFruit 5' : ['2013-07-02', 'phone'],
	'iFruit 5A' : ['2013-10-02', 'phone'],

	'Ronin S1' : ['2010-05-20', 'phone'],
	'Ronin S2' : ['2012-07-21', 'phone'],
	'Ronin S3' : ['2013-02-11', 'phone'],
	'Ronin S4' : ['2013-10-02', 'phone'],
	'Ronin Novelty Note 1' : ['2010-06-20', 'phone'],
	'Ronin Novelty Note 2' : ['2011-10-02', 'phone'],
	'Ronin Novelty Note 3' : ['2013-04-11', 'phone'],
	'Ronin Novelty Note 4' : ['2013-07-02', 'phone'],

	'MeeToo 1.0' : ['2008-10-21', 'phone'],
	'MeeToo 2.0' : ['2010-05-15', 'phone'],
	'MeeToo 3.0' : ['2011-02-18', 'phone'],
	'MeeToo 3.1' : ['2011-09-21', 'phone'],
	'MeeToo 4.0' : ['2012-07-21', 'phone'],
	'MeeToo 4.1' : ['2012-08-04', 'phone'],
	'MeeToo 5.0' : ['2012-09-21', 'phone'],
	'MeeToo 5.1' : ['2013-08-01', 'phone'],

	'Titanic 1000' : ['2008-10-21', 'phone'],
	'Titanic 1100' : ['2008-11-25', 'phone'],
	'Titanic 2000' : ['2009-12-21', 'phone'],
	'Titanic 2100' : ['2010-04-19', 'phone'],
	'Titanic 2200' : ['2010-05-25', 'phone'],
	'Titanic 2300' : ['2011-02-18', 'phone'],
	'Titanic 2400' : ['2011-09-21', 'phone'],
	'Titanic 2500' : ['2012-07-21', 'phone'],
	'Titanic 3000' : ['2012-08-04', 'phone'],
	'Titanic 4000' : ['2012-09-21', 'phone'],
	'Titanic DeckChairs' : ['2013-08-01', 'phone'],
}

# Key = first 3 digits of zip code
# Then:
# City
# State
# Area Code
zipCode94 = {
'940' : ['San Francisco', 'CA', '415'],
'941' : ['San Francisco', 'CA', '415'],
'942' : ['Sacramento', 'CA', '916'],
'943' : ['Palo Alto', 'CA', '650'],
'944' : ['San Mateo', 'CA', '650'],
'945' : ['Oakland', 'CA', '510'],
'946' : ['Oakland', 'CA', '510'],
'947' : ['Berkeley', 'CA', '510'],
'948' : ['Richmond', 'CA', '510'],
'949' : ['Santa Rosa', 'CA', '707']
}

zipCode95 = {
'950' : ['San Jose', 'CA', '408'],
'951' : ['San Jose', 'CA', '408'],
'952' : ['Stockton', 'CA', '209'],
'953' : ['Stockton', 'CA', '209'],
'954' : ['Santa Rosa', 'CA', '707'],
'955' : ['Eureka', 'CA', '707'],
'956' : ['Sacramento', 'CA', '916'],
'957' : ['Sacramento', 'CA', '916'],
'958' : ['Sacramento', 'CA', '916'],
'959' : ['Marysville', 'CA', '530'],
}

zipCodeRestOfCAAndOR = {
'900' : ['Los Angeles', 'CA', '213'],
'901' : ['Los Angeles', 'CA', '213'],
'902' : ['Inglewood', 'CA', '310'],
'903' : ['Inglewood', 'CA', '310'],
'904' : ['Santa Monica', 'CA', '310'],
'905' : ['Torrance', 'CA', '424'],
'906' : ['Long Beach', 'CA', '562'],
'907' : ['Long Beach', 'CA', '562'],
'908' : ['Long Beach', 'CA', '562'],
# 909 Not in use
'910' : ['Pasadena', 'CA', '626'],
'911' : ['Pasadena', 'CA', '626'],
'912' : ['Glendale', 'CA', '747'],
'913' : ['Van Nuys', 'CA', '818'],
'914' : ['Van Nuys', 'CA', '818'],
'915' : ['Burbank', 'CA', '818'],
'916' : ['North Hollywood', 'CA', '818'],
'917' : ['Industry', 'CA', '626'],
'918' : ['Alhambra', 'CA', '626'],
'919' : ['San Diego', 'CA', '619'],
'920' : ['San Diego', 'CA', '619'],
'921' : ['San Diego', 'CA', '619'],
'922' : ['Palm Springs', 'CA', '760'],
'923' : ['San Bernardino', 'CA', '909'],
'924' : ['San Bernardino', 'CA', '909'],
'925' : ['Riverside', 'CA', '951'],
'926' : ['Santa Ana', 'CA', '657'],
'927' : ['Santa Ana', 'CA', '657'],
'928' : ['Anaheim', 'CA', '714'],
# 929 Not in use
'930' : ['Oxnard', 'CA', '805'],
'931' : ['Santa Barbara', 'CA', '805'],
'932' : ['Bakersfield', 'CA', '661'],
'933' : ['Bakersfield', 'CA', '661'],
'934' : ['Santa Barbara', 'CA', '805'],
'935' : ['Mojave', 'CA', '661'],
'936' : ['Fresno', 'CA', '559'],
'937' : ['Fresno', 'CA', '559'],
'938' : ['Fresno', 'CA', '559'],
'939' : ['Salinas', 'CA', '831'],
'960' : ['Redding', 'CA', '530'],
# Start of Oregon
'970' : ['Portland', 'OR', '503'],
'971' : ['Portland', 'OR', '503'],
'972' : ['Portland', 'OR', '503'],
'973' : ['Salem', 'OR', '503'],
'974' : ['Eugene', 'OR', '541'],
'975' : ['Medford', 'OR', '541'],
'976' : ['Klamath Falls', 'OR', '541'],
'977' : ['Bend', 'OR', '541'],
'978' : ['Pendleton', 'OR', '541']
}

zipCodeNV = {
'889' : ['Las Vegas', 'NV', '702'],
'890' : ['Las Vegas', 'NV', '702'],
'891' : ['Las Vegas', 'NV', '702'],
# 892 Not in use
'893' : ['Ely', 'NV', '775'],
'894' : ['Reno', 'NV', '775'],
'895' : ['Reno', 'NV', '775'],
# 896 Not in use
'897' : ['Carson City', 'NV', '775'],
'898' : ['Elko', 'NV', '775']
}

zipCodeArizona = {
'850' : ['Phoenix', 'AZ', '928'],
'851' : ['Phoenix', 'AZ', '928'],
'852' : ['Phoenix', 'AZ', '928'],
'853' : ['Phoenix', 'AZ', '928'],
# 854 Not in use
'855' : ['Globe', 'AZ', '928'],
'856' : ['Tucson', 'AZ', '928'],
'857' : ['Tucson', 'AZ', '928'],
# 858 Not in use
'859' : ['Show Low', 'AZ', '928'],
'860' : ['Flagstaff', 'AZ', '928'],
# 861 Not in use
# 862 Not in use
'863' : ['Prescott', 'AZ', '928'],
'864' : ['Kingman', 'AZ', '928']
}

zipCodeAll = {}
zipCodeAll.update(zipCode94)
zipCodeAll.update(zipCode95)
zipCodeAll.update(zipCodeRestOfCAAndOR)
zipCodeAll.update(zipCodeNV)
zipCodeAll.update(zipCodeArizona)

accountQueue = Queue.Queue(maxsize=0)

def fillAccountQueue(currentDate):
	for account in Account.select().where(
			(Account.acct_create_dt < currentDate) &
			((Account.acct_close_dt == None) | (Account.acct_close_dt < currentDate))
		).order_by(fn.Rand()).limit(500):
		accountQueue.put(account)

kbArticleTypes = {
	'ringtone' : 'ringtone',
	'contacts' : 'contacts',
	'backups' : 'backups',
	'reboot' : 'reboot',
	'batterylife' : 'batterylife',
	'overheat' : 'overheat',
}

# One account should have every piece of data for it
demoAccount = 1