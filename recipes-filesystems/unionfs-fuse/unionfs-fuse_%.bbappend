DEPENDS += " pkgconfig-native"

RDEPENDS_${PN} = "vyos-bash"

PNBLACKLIST[unionfs-fuse] = ""
