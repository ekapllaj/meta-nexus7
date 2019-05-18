# graphics drivers are provided by nv-drivers.bb recipe, so remove 
# egl and gles packages from mesa recipe (this fix the compile error
# on nv-drivers.bb :
# 	Error:	The recipe nv-drivers is trying to install files into a 
# 	shared area when those files already exist. 
PACKAGECONFIG_remove = "egl gles"

PROVIDES_remove = "virtual/libgles1 virtual/libgles2 virtual/egl"
