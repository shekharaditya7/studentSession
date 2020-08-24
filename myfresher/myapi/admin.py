from django.contrib import admin
from .models import Session, Student
# Register your models here.

admin.site.register(Student)
admin.site.register(Session)