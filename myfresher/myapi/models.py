from django.db import models

# Create your models here.

SLOTS = (
        ('NONE' , ('Select a slot')),
        ('1', ('Time 9:00 am to 11:00 am')),
        ('2' ,('Time 11:00 am to 1:00 pm')),
        ('3' ,('Time 3:00 pm to 5:00 pm')),
        
    )



class Session(models.Model):
	title =models.CharField(max_length=100 , blank=False)
	description = models.CharField(max_length=500 , blank=False)
	slots = models.CharField(max_length = 100,choices = SLOTS , null = False)
	session_date = models.DateField(verbose_name='Session Date', null =False)

	def __str__(self):
		return str(self.title+"  "+self.slots)
    
	
class Student(models.Model):
	username = models.CharField(max_length=60,primary_key=True, verbose_name='username')
	name = models.CharField(max_length=60)
	isadmin = models.BooleanField(default=False)
	sessions  = models.ManyToManyField(Session, blank=True)

	def __str__(self):
		return self.username