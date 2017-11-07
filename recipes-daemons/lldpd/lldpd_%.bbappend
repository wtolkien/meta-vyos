
# disable /etc/init.d/lldpd startup script (VyOS takes care of this)
INITSCRIPT_PARAMS_${PN} = "remove"
