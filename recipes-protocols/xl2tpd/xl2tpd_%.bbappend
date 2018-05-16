FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://001-initscript.patch"

# prevent automatic statup using bitbake's update-rc.d class,
# VyOS will take care of this
INHIBIT_UPDATERCD_BBCLASS = "1"
