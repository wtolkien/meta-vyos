# VyOS expects brctl in '/sbin', install link...
do_install_append () {
    install -d ${D}/sbin
    ln -s /usr/sbin/brctl.${BPN} ${D}/sbin/brctl
}
