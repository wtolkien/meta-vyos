# VyOS expects ip in '/bin', as well as in '/sbin', OE installs it in '/sbin' only
do_install_append () {
    install -d ${D}/bin
    ln -s /sbin/ip.iproute2 ${D}/bin/ip
}
