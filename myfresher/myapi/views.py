from rest_framework import routers, serializers, viewsets, status
from rest_framework.response import Response
from django.contrib.auth.models import User
from .serializers import StudentSerializer, SessionSerializer
from .models import Student, Session
from rest_framework import filters
from django.utils.decorators import method_decorator
from django.views.decorators.cache import cache_page
from django.core.cache import cache

class StudentViewSet(viewsets.ModelViewSet):
	"""
	List all students or Create a New Student
	"""
	queryset = Student.objects.all()
	serializer_class = StudentSerializer

	
	def retrieve(self, request, *args, **kwargs):
		instance = self.get_object()
		serializer = self.get_serializer(instance)
		return Response(serializer.data)


	def partial_update(self, request, *args, **kwargs):
		cache.delete('session')
		print("Cache Deleted")
		
		#q = Student.objects.get(pk=request.data.get('username'))
		#request.data['name'] = q.name
		print(request.data)
		kwargs['sessions'] = request.data.get('sessions')
		return self.update(request, *args, **kwargs)



	




class SessionViewSet(viewsets.ModelViewSet):
	"""
	List all sessions or create a new Session
	"""
	
	serializer_class = SessionSerializer
	queryset = Session.objects.all()

	
	def list(self, request):
		if 'session' in cache:
			sessions = cache.get('session')
			print("Found in Cache\n")
			return Response(sessions)
		q = Session.objects.all()
		serializer = self.get_serializer(q, many=True)
		cache.set('session', serializer.data, timeout = 60*60)
		return Response(serializer.data)




	def create(self, request):
		serializer = self.get_serializer(data=request.data)
		serializer.is_valid(raise_exception=True)
		self.perform_create(serializer)
		headers = self.get_success_headers(serializer.data)
		cache.delete('session')
		print("Cache deleted")
		return Response(serializer.data, status=status.HTTP_201_CREATED, headers=headers)


	def destroy(self, request, *args, **kwargs):
		instance = self.get_object()
		self.perform_destroy(instance)
		cache.delete('session')
		print("Cache Deleted")
		return Response(status = status.HTTP_204_NO_CONTENT)

	
