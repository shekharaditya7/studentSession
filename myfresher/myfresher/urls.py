"""myfresher URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path, include
from django.contrib.auth.models import User
from myapi.views import StudentViewSet, SessionViewSet
from rest_framework.routers import DefaultRouter

from rest_framework import routers
from rest_framework.authtoken.views import obtain_auth_token


# Routers provide an easy way of automatically determining the URL conf.

router = DefaultRouter()
router.register('api/students', StudentViewSet, basename='students')
router.register('api/sessions', SessionViewSet, basename='sessions')


# Wire up our API using automatic URL routing.
# Additionally, we include login URLs for the browsable API.

urlpatterns = [
    path('admin/', admin.site.urls),    
    path('', include(router.urls)),
    #path('api/', include('rest_framework.urls', namespace='rest_framework')),
    #path('api-token-auth/', obtain_auth_token, name='api-token-auth')
]
