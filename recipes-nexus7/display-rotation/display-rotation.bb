SUMMARY = "Display rotation script for nexus7 2012 tablet"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PR = "r0"

COMPATIBLE_MACHINE = "(nexus7)"

SRC_URI_nexus7 = " \
	file://display-rotation.sh \
	file://display-rotation.desktop \
	file://display-rotation-icon.png \
"

do_install(){
	install -d ${D}/usr/local/sbin
	install -m 755 ${WORKDIR}/display-rotation.sh ${D}/usr/local/sbin

	install -d ${D}/usr/share/applications/
	install -m 644 ${WORKDIR}/display-rotation.desktop ${D}/usr/share/applications

	install -d ${D}/usr/share/pixmaps
	install -m 644 ${WORKDIR}/display-rotation-icon.png ${D}/usr/share/pixmaps
}

FILES_${PN} = "/usr"
