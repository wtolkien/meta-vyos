# meta-vyos
A meta layer to bring VyOS router functionality to OpenEmbedded

VyOS is an open source network operating system based on GNU/Linux. It joins
multiple applications such as Quagga, ISC DHCPD, OpenVPN, StrongS/WAN and
others under a single management interface.

More information can be found at https://vyos.io

VyOS is based on Debian and currently available as a LiveCD/Install ISO for
physical and virtual 64-bit x86 machines and VMWare OVA for vSphere/vCloud Air.

So far there are no official builds for other processor architectures or embedded
platforms, however an ARM build for SolidRun's ClearFog platform does exist:

http://dev.packages.vyos.net/devices/clearfog/solidrun/

While Debian is available for many different processor architectures, it was never
explicitly designed for embedded systems. As a result, embedded platform support
lags far behind compared to other build environments such as OpenEmbedded / Yocto.

This project aims to create a meta layer for OpenEmbedded / Yocto that will make it
easy to run VyOS router software on a wide variety of embedded platforms.

The project is currently at a very early stage: Some core VyOS packages have been
converted to OpenEmbedded and it is possible to start up a VyOS image in Qemu and
try out VyOS's command completion feature. That's about it.

There is a lot more work to do and any help from interested parties is very welcome.

## Some of the immediate issues:

- Privileged mode does not yet work: Once the 'configure' command has been
  entered, command completion and the build-in CLI help system no longer works.
  This could be related to permission problems or problems with the unionfs-fuse
  sytem that's used by Debian VyOS
- Config load/save is not yet working. This is likely related to the way how
  a config partition is traditionally mounted in Debian VyOS.
- Image management is not working, and it may never work in the same way usually
  does with Debian VyOS. Debian VyOS uses Debian's LiveCD architecture with
  initramfs/squashfs to handle multiple images. It is not clear yet how this
  translates into the embedded realm, or even if it makes sense at all.
- So far there are bitbake recipes for only a select few core VyOS packages,
  so functionality will be limited at best and more bitbake recipes will have to
  be created.
- There are likely going to be a fair number of other bugs that stem from
  differences between core Debian vs. OpenEmbedded packages.


## Rough roadmap:

- Resolve issues with privileged mode and configuration load/save.
- Make sure some very basic config commands work, i.e. setting an Ethernet
  interface IP address
- Port over more VyOS features by creating recipes for other VyOS packages
- Resolve issues related to image management / firmware upgrade. Either find a
  way to use the existing 'VyOS way' with OpenEmbedded, or design something new
  that possibly makes more sense in an embedded environment
- Deal with issues related to logging and frequent file system write access that
  could wear out an embedded flash filesystem
- Debug!


# Build Instructions:

Development is currently done against Yocto 2.3 ('pyro'), however other
OpenEmbedded-based distributions will likely work as well. If you are not familiar
with OpenEmbedded/Yocto, extensive documentation can be found here:

https://www.yoctoproject.org/documentation

* get the OpenEmbedded/Yocto source as well as this project's source:
```
git clone -b pyro git://git.yoctoproject.org/poky.git
git clone https://github.com/wtolkien/meta-vyos.git
```
* change into the Yocto directory and initialize the build-environment:
```
cd poky
source oe-init-build-env
```
* edit Yocto layer configuration file ```conf/bblayers.conf``` to include the
  meta-vyos layer: add an entry pointing to the meta-vyos layer to the list
  of included layers - adjust your path as required:
```
BBLAYERS ?= " \
      /opt/src/poky/meta \
      /opt/src/poky/meta-poky \
      /opt/src/poky/meta-yocto-bsp \
      /opt/src/meta-vyos \
      "
```
* edit the Yocto config file ```conf/local.conf``` to build a 'vyos' distro and use
  the 'deb' package format (the latter isn't strictly required, but recommended
  due to VyOS's Debian heritage):
```
[...]
DISTRO ?= "vyos"
[...]
PACKAGE_CLASSES ?= "package_deb"
```
* you are now ready to start the build process. From the ```poky/build``` directory,
  enter:
```
bitbake vyos-image
```
* When done (likely after 45 minutes or more), an image will be in the
  ```poky/build/tmp/deploy/image/qemux86``` directory. It can be started by entering
```
runqemu
```

## Acknowledgements:

Credit goes to the VyOS team for contributing and maintaining such a great router platform! Kudos 
to Kim Hagen for having ported VyOS to the ClearFog platform and thereby inspiring this project!
