SUMMARY = "Linux Kernel for Nexus 7 tablet"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel
LINUX_VERSION ?= "3.1.10"

LOCALVERSION = "nexus7-1.0.0"
PR = "r0"

SRCREV = "025be093d300a98b5f58cfd17cb3aaf22f1ac1b6"

PV = "${LINUX_VERSION}-${LOCALVERSION}"
S = "${WORKDIR}/git"

SRCBRANCH = "master"
#SRC_URI = "git://git.toradex.com/linux-toradex.git;protocol=git;branch=${SRCBRANCH}"
SRC_URI = "\
	git:///home/asus/work/kernel-sources/ubuntu-nexus7;protocol=file;branch=${SRCBRANCH}\
	file://timeconst-pl.patch \
	file://nexus7_defconfig \
"

COMPATIBLE_MACHINE = "nexus7"

do_configure_prepend () {
	# use defconfig extracted from a running ubuntu 13.04 on nexus 7
	DEFCONFIG="nexus7_defconfig"
	cp ${WORKDIR}/${DEFCONFIG} ${S}/arch/arm/configs/
	cd ${S}
	export KBUILD_OUTPUT=${B}
	oe_runmake $DEFCONFIG

	#Overwrite LOCALVERSION on .config
	sed -i -e /CONFIG_LOCALVERSION/d ${B}/.config
	echo "CONFIG_LOCALVERSION=\"${LOCALVERSION}\"" >> ${B}/.config
}
