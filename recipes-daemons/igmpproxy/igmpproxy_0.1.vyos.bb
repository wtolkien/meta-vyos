SUMMARY = "simple dynamic multicast routing daemon that only uses IGMP signalling \
           (VyOS version with Ubiquiti ERL patches)"
HOMEPAGE = "https://github.com/vyos/igmpproxy"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=1e995e2799bb0d27d63069b97f805420"


SRC_URI = "git://github.com/vyos/igmpproxy.git;branch=current;protocol=https \
          "
SRCREV = "1f87c675725bff8f380bf7364c3961ee5a5a9e4f"

S = "${WORKDIR}/git"

inherit autotools pkgconfig
