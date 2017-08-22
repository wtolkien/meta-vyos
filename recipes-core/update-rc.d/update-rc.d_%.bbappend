FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://init-functions \
            file://invoke-rc.d \
            "

FILES_${PN} += "/lib"

do_install_append () {
    # install missing Debian invoke-rc.d script
    install -m 0755 ${WORKDIR}/invoke-rc.d ${D}${sbindir}

    # Linux Standard Base (LSB) init functions
	install -d ${D}/lib/lsb
	install -m 0755 ${WORKDIR}/init-functions ${D}/lib/lsb
}
