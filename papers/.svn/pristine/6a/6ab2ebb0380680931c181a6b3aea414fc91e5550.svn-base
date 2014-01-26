void IA32_MMU::map(Phy_Addr addr, int from, int to, Page_Flags flags) {
    addr = align_page(addr);
    while(from < to)  { _pt[from++] = addr | flags;
			     addr+= sizeof(Page);  }
}

void H8_MMU::map(Phy_Addr addr, int from, int to, Page_Flags flags) { }
