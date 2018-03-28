# include all required individual subpackages
RDEPENDS_${PN} += " \
	iproute2-tc \
	iproute2-ss \
	iproute2-nstat \
	iproute2-rtacct \
	iproute2-genl \
	iproute2-ifstat \
	iproute2-lnstat \
    "

# arpd requires berkeley DB with DB 1.85 compatibility API
DEPENDS += "db"

# also compile 'netem' subdirectory
EXTRA_OEMAKE += "SUBDIRS='lib tc ip bridge misc netem genl'"

FILES_${PN} += "/usr/lib/tc"

# fix various path issues between the VyOS package and the OE package
# not all of these symlinks may be necessary
do_install_append () {
    install -d ${D}/bin
    ln -s /sbin/ip.iproute2 ${D}/bin/ip
    ln -s /sbin/ss ${D}/bin

    install -d ${D}/usr/sbin
    ln -s /sbin/arpd ${D}/usr/sbin

    install -d ${D}/usr/bin
    ln -s /sbin/ctstat ${D}/usr/bin
    ln -s /sbin/lnstat ${D}/usr/bin
    ln -s /sbin/nstat ${D}/usr/bin
    ln -s /sbin/routef ${D}/usr/bin
    ln -s /sbin/routel ${D}/usr/bin
    ln -s /sbin/rtstat ${D}/usr/bin
}
