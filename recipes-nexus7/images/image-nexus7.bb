DESCRIPTION = "Image with Sato, a mobile environment and visual style for \
mobile devices. The image supports X11 with a Sato theme, Pimlico \
applications, and contains terminal, editor, and file manager."

IMAGE_FEATURES += "splash package-management x11-base x11-sato hwcodecs"

LICENSE = "MIT"

inherit core-image

IMAGE_INSTALL_append ="\
	openssh \
	systemd-analyze \
	networkmanager \
	network-manager-applet \
	alsa-plugins \
	alsa-lib \
	alsa-utils \
	vim \
	display-rotation \
	tree \
	ldd \
	usbutils \
"

IMAGE_INSTALL += "packagegroup-core-x11-sato-games"
