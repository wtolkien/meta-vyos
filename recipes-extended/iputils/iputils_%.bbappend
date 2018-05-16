# include all required individual subpackages
RDEPENDS_${PN} += " \
	iputils-arping \
	iputils-ping \
    "

# add 'arping' link in /usr/bin to match VyOS/Debian
do_install_append() {
    install -m 0755 -d ${D}${bindir}
    ln -s ${base_bindir}/arping ${D}${bindir}
}

FILES_${PN}-arping += "${bindir}/arping"
