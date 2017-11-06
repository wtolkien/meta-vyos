FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://001-initscript.patch"

# disable /etc/init.d/xl2tpd startup script (VyOS takes care of this)
INITSCRIPT_PARAMS_${PN} = "remove"
