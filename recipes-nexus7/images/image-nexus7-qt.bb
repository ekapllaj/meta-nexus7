DESCRIPTION = "Nexus 7 Image with QT5"

LICENSE = "MIT"

require image-nexus7.bb

IMAGE_INSTALL_append ="\
	qtbase \
	qtsmarthome \
	qtsvg \
	qt5-demo-extrafiles \
	cinematicexperience \
	qt5everywheredemo \
	qt5ledscreen \
	qt5nmapcarousedemo \
	qt5-opengles2-test \
	quitbattery \
	quitindicators \
"

