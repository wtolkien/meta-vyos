# Copyright (C) 2017 Aaron Brice <aaron.brice@datasoft.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Administration tool for IP sets"
HOMEPAGE = "http://ipset.netfilter.org"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"
SECTION = "base"

DEPENDS = "libtool libmnl"

inherit autotools pkgconfig

SRC_URI = "http://ipset.netfilter.org/${PN}-${PV}.tar.bz2"

SRC_URI[md5sum] = "51bd03f976a1501fd45e1d71a1e2e6bf"
SRC_URI[sha256sum] = "d70e831b670b7aa25dde81fd994d3a7ce0c0e801559a557105576df66cd8d680"

EXTRA_OECONF += "--with-kmod=no"
