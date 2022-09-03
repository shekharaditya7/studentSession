from rest_framework import serializers

from django.contrib.auth.models import User
from .models import Session, Student


class StudentSerializer(serializers.ModelSerializer):
	class Meta:
		model = Student
		fields = '__all__'
		extra_kwargs = {'sessions': {'required': False}}


class SessionSerializer(serializers.ModelSerializer):
	student_set = StudentSerializer(many=True, read_only=True)

	class Meta:
		model = Session
		fields = '__all__'
		extra_kwargs = {'student_set': {'required': False}}

	