SUMMARY = "binary files from Nvidia along with their configuration, adapted for nexus 7 machine from meta-toradex"
LICENSE = "CLOSED & SGI & Khronos"
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(nexus7)"

FILES_${PN}-dev = ""

PROVIDES += "virtual/egl virtual/libgles1 virtual/libgles2"
DEPENDS = "gstreamer gst-plugins-base libpcre virtual/xserver virtual/libx11 libxdamage libxext libxfixes"
#RDEPENDS_${PN}-nv-gstapps = "libpcre libpcreposix"

SRC_URI_nexus7 = " \
    http://developer.download.nvidia.com/mobile/tegra/l4t/r16.5.0/cardhu_release_armhf/Tegra30_Linux-codecs_R16.5_armhf.tbz2;name=Tegra30_Linux-codecs \
    http://developer.download.nvidia.com/mobile/tegra/l4t/r16.5.0/cardhu_release_armhf/Tegra30_Linux_R16.5_armhf.tbz2;name=Tegra30_Linux \
    file://egl.pc \
    file://eglplatform.h \
    file://gles.pc \
    file://glesv2.pc \
    file://khronos_headers.tgz \
"

SRC_URI[Tegra30_Linux-codecs.md5sum] = "03a0b726f18a26ed379c62b83119e497"
SRC_URI[Tegra30_Linux-codecs.sha256sum] = "640df86c47d0dd6cbfc15eff49ee146a964c2a0362fe52ee6880c84e08b7a98b"

SRC_URI[Tegra30_Linux.md5sum] = "8759be1c7b93511cf70939df28b8af51"
SRC_URI[Tegra30_Linux.sha256sum] = "2288ad9ce30239975208cbd5fd896791c9d37d87ca8d30b51f04bacc74835427"

# xserver-xorg driver ABI version to be used by the symlink, must match the required ABI version from the used xserver
XSERVER_DRIVER_ABI_REQUIRED = "14"

LIC_FILES_CHKSUM = " \
    file://${WORKDIR}/Linux_for_Tegra/nv_tegra/LICENSE;md5=d88b0db2ba7f6dfe70852c64b2fce6ce \
    file://${WORKDIR}/Linux_for_Tegra/nv_tegra/nv_sample_apps/LICENSE.gst-openmax;md5=a7d467726825e72551082b781a94d09d \
"


#no gnu_hash in NVIDIA binaries, skip QA ldflags
#we have symlinks ending in .so, skip QA dev-so
#the qa check is not able to follow the libpcre symlink, skip QA file-rdeps
#inhibit warnings about files being stripped
INSANE_SKIP_${PN} = "dev-so ldflags already-stripped textrel"
INSANE_SKIP_${PN}-nv-gstapps = "dev-so ldflags already-stripped textrel file-rdeps"


do_compile[noexec]="1"

do_install () {
	export LDK_ROOTFS_DIR=${D}
	# delete lines that install vmlinux, we are not installing it.
	sed -i '/vmlinux/d' ${WORKDIR}/Linux_for_Tegra/apply_binaries.sh
	cd ${WORKDIR}/Linux_for_Tegra && ./apply_binaries.sh --xabi 14

	# remove libjpeg.so, it is failling to install because some other recipe installs it
	rm ${D}/usr/lib/libjpeg.so

	# remove nvidia gst binasies, they require libpcre.so.3 wich I couldn't make it work
	rm ${D}/usr/bin/nvgst*

	export LIBNAME=`ls ${D}/usr/lib/libGLESv2.so.?`
	export LIBNAME=`basename $LIBNAME`
	ln -s $LIBNAME ${D}/usr/lib/libGLESv2.so
	export LIBNAME=`ls ${D}/usr/lib/libEGL.so.?`
	export LIBNAME=`basename $LIBNAME`
	ln -s $LIBNAME ${D}/usr/lib/libEGL.so
	export LIBNAME=`ls ${D}/usr/lib/libGLESv1_CM.so.?`
	export LIBNAME=`basename $LIBNAME`
	ln -s $LIBNAME ${D}/usr/lib/libGLESv1_CM.so

	# .pc files are required by pkg-config, else other packages will fail with
	# No package 'egl' found, ... See the pkg-config man page for more details.
	install -d  ${D}/usr/lib/pkgconfig
	install -m 0644 ${WORKDIR}/*.pc ${D}/usr/lib/pkgconfig/

	#restricted codecs
	mkdir -p restricted_codecs
	tar -C restricted_codecs -xjf ${WORKDIR}/restricted_codecs.tbz2

	#nvidia restricted codecs
	install -d ${D}/lib/firmware/
	install -m 0644 restricted_codecs/lib/firmware/* ${D}/lib/firmware/
	
	#khronos headers for EGL/GLES/GLES2/OpenMax
	for dir in EGL GLES GLES2 KD KHR
	do
	    install -d ${D}${includedir}/$dir
	    install -m 0644 ${WORKDIR}/khronos_headers/$dir/* ${D}${includedir}/$dir
	done

	install -d ${D}${includedir}/EGL
	#Override eglplatform.h that khronos provide.
	install -m 0644 ${WORKDIR}/eglplatform.h ${D}${includedir}/EGL/
}

# Add the ABI dependency at package generation time, as otherwise bitbake will
# attempt to find a provider for it (and fail) when it does the parse.
python populate_packages_prepend() {
    pn = d.getVar("PN", True)
    d.appendVar("RDEPENDS_" + pn, " xorg-abi-video-${XSERVER_DRIVER_ABI_REQUIRED}")
}
FILES_${PN} +="/usr /lib /etc"
