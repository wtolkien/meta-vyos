# need to create a 'libperl.so' link for vyatta-cfg
do_install_append() {
    ln -sf libperl.so.${PV} ${D}/${libdir}/libperl.so
}
INSANE_SKIP_perl-lib = "dev-so"


# we required *.ph perl header files, however these need to be generated
# on the target system since the 'h2ph' tool can not cross-compile:
# a) install required *.h headers in temporary directory
# b) run 'h2ph' on first target power-up, then delete *.h files

do_install_append () {
	install -d ${D}/usr/lib/perl/include
	install ${PKG_CONFIG_SYSROOT_DIR}${includedir}/*.h ${D}/usr/lib/perl/include 
	install -d ${D}/usr/lib/perl/include/asm
	install ${PKG_CONFIG_SYSROOT_DIR}${includedir}/asm/*.h ${D}/usr/lib/perl/include/asm
	install -d ${D}/usr/lib/perl/include/asm-generic
	install ${PKG_CONFIG_SYSROOT_DIR}${includedir}/asm-generic/*.h ${D}/usr/lib/perl/include/asm-generic 
	install -d ${D}/usr/lib/perl/include/bits
	install ${PKG_CONFIG_SYSROOT_DIR}${includedir}/bits/*.h ${D}/usr/lib/perl/include/bits 
	install -d ${D}/usr/lib/perl/include/gnu
	install ${PKG_CONFIG_SYSROOT_DIR}${includedir}/gnu/*.h ${D}/usr/lib/perl/include/gnu 
	install -d ${D}/usr/lib/perl/include/linux
	install ${PKG_CONFIG_SYSROOT_DIR}${includedir}/linux/*.h ${D}/usr/lib/perl/include/linux
	install -d ${D}/usr/lib/perl/include/sys
	install ${PKG_CONFIG_SYSROOT_DIR}${includedir}/sys/*.h ${D}/usr/lib/perl/include/sys
	install -d ${D}${libdir}/perl/site_perl/${PV} 
}

FILES_${PN}-lib_append = " \
		${libdir}/perl/include \
		${libdir}/perl/site_perl/${PV} \
		"
		
pkg_postinst_${PN} () {
	# make sure this gets only executed on the device, not at build time
	if [ x"$D" = "x" ]; then
		cd /usr/lib/perl/include
		h2ph -r .
		cd /
		rm -rf /usr/lib/perl/include 
	else
   		exit 1
	fi
}