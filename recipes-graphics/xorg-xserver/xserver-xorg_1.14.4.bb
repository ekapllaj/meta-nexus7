require xserver-xorg.inc

# Misc build failure for master HEAD
SRC_URI += "file://crosscompile.patch \
            file://fix_open_max_preprocessor_error.patch \
            file://mips64-compiler.patch \
            file://aarch64.patch \
           "
SRC_URI_append_nexus7 = " file://Revert_Use_new_pixman_glyph_cache_t_API_that_will_be_in_pixman0.28.0.patch"

SRC_URI[md5sum] = "9d68a30258c67faa3c036a4a85e8bf97"
SRC_URI[sha256sum] = "608ccfaafb845f6e559884a30f946d365209172416710d687b190e9e1ff65dc3"

# These extensions are now integrated into the server, so declare the migration
# path for in-place upgrades.

RREPLACES_${PN} =  "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "
RPROVIDES_${PN} =  "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "
RCONFLICTS_${PN} = "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "

do_install_append(){
	install -d ${D}/usr/share/X11/xorg.conf.d
	rm ${D}/usr/share/X11/xorg.conf.d/10-evdev.conf
}
