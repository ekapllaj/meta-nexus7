FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI +="\
	file://HiFi \
	file://tegra-rt5640.conf \
"

do_install_append(){
	install -d ${D}/usr/share/alsa/ucm
	install -m 644 ${WORKDIR}/HiFi ${D}/usr/share/alsa/ucm/
	install -m 644 ${WORKDIR}/tegra-rt5640.conf ${D}/usr/share/alsa/ucm/
}
