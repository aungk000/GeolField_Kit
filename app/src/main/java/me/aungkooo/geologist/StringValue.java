package me.aungkooo.geologist;



public class StringValue
{
    /* My Notes Location */

    public static String[] rockColors = {
            "black","blackish red","bluish white","brilliant green",
            "brownish black","brownish gray","dark greenish gray","dark greenish yellow",
            "dark reddish brown","dark yellowish brown","dark yellowish green",
            "dark yellowish orange","dusky blue","dusky blue-green","dusky brown","dusky green",
            "dusky red","dusky yellow","dusky yellow green","dusky yellowish brown",
            "dusky yellowish green","grayish black","grayish blue","grayish blue-green",
            "grayish brown","grayish green","grayish olive","grayish olive green",
            "grayish orange","grayish orange pink","grayish pink","grayish purple","grayish red",
            "grayish red purple","grayish yellow","grayish yellow green","greenish black",
            "greenish gray","light blue","light blue green","light bluish gray","light brown",
            "light brownish gray","light gray","light green","light greenish gray","light olive",
            "light olive brown","light olive-gray","light red","medium bluish gray","medium gray",
            "medium light gray","moderate blue","moderate blue green","moderate brown",
            "moderate green","moderate greenish yellow","moderate olive brown",
            "moderate orange pink","moderate pink","moderate red","moderate reddish brown",
            "moderate reddish orange","moderate yellow","moderate yellowish green","olive black",
            "olive gray","pale blue","pale blue green","pale brown","pale green",
            "pale greenish yellow","pale olive","pale pink","pale purple","pale red",
            "pale red purple","pale reddish brown","pale yellowish brown","pale yellowish green",
            "pale yellowish orange","very dark red","very dusky purple","very dusky red",
            "very dusky red purple","very light gray","very pale blue","very pale green",
            "very pale orange","white","yellowish gray"
    };

    public static String[] textures = {
            "Adamantine", "Adcumulate","Agglomeritic","Amygdaloidal","Anhedral",
            "Antitaxial veins","Aphanitic","Aphanitic","Aplitic","Augen textured gneiss",
            "Axiolitic texture","Bedding fissile","Bedding fissility","Botryoidal","Boudinage",
            "Boudins","Brecciated","Cataclastic","Chilled margin","Clastic","Cleaved","Crenulated",
            "Cross-bedding","Cross-stratification","Cumulate","Decussate","Dendritic texture",
            "Devitirification","Diatextite","Embayed minerals","Epiclastic","Equigranular",
            "Euhedral", "Eutaxitic","Fiamme","Fissile","Foliation","Fossiliferous",
            "Glomeroporphyritic","Gneissic","Gneissose","Granoblastic","Granophyric",
            "Granulitic","Graphic","Holocrystalline","Hyaline_texture","Imbricate","Jointed",
            "Kelyphitic","Komatiite","Lepidoblastic","Leucocratic","Lineation","Lineated",
            "L-tectonite","Melanocratic","Mesocratic","Mesocumulate","Migmatite","Mylonitic",
            "Nematoblastic","Obsidian","Ocelli","Oolitic","Ophitic texture","Orbiculartexture",
            "Orthocumulate","Panidiomorphic","Pegmatitic","Peloidal","Perthitic","Phaneritic",
            "Phyllitic","Pisolitic","Poikilitic","Porphyritic texture","Porphyroblastic",
            "Porphyroclastic","Ptygmatic(folding)","Quench textures","Rapakivi texture","Sandy",
            "Schistose","Schistosity","Schlieren", "texture","Shear","Shear fabric","Sheared",
            "Slaty","Slaty cleavage","Specular","Spherulite","Spinifex texture","S-tectonite",
            "Stratabound","Stratum","Stromatolitic","Stylolitic","Subhedral","Symplectite",
            "Tachylyte","Trachytic texture","Tuffaceous","Variolitic","Vesicular texture",
            "Vitreous","Vuggy"
    };

    public static String[] rockUnits = {
            "Adakite","Adamellite","Alkali feldspar granite","Amphibolite","Andesite","Anorthosite",
            "Anthracite","Aphanite","Aplite","Appinite","Argillite","Arkose","Banded iron formation",
            "Basalt","Basaltic  trachyandesite","Basanite","Benmoreite","Blairmorite","Blue Granite",
            "Blueschist","Boninite","Borolanite","Breccia","Calcarenite","Calcflinta","Carbonatite",
            "Cataclasite","Chalk","Charnockite","Chert","Claystone","Coal","Comendite","Conglomerate",
            "Coquina","Dacite","Diabase","Diamictite","Diatomite","Diorite","Dolerite","Dolomite",
            "Dolostone","Dunite","Eclogite","Enderbite","Epidosite","Essexite","Evaporite","Felsite",
            "Flint","Foidolite","Gabbro","Ganister","Geyserite","Gneiss","Gossan","Granite",
            "Granodiorite","Granophyre","Granulite","Greenschist","Greywacke","Gritstone",
            "Harzburgite","Hawaiite","Hornblendite","Hornfels","Hyaloclastite","Icelandite",
            "Ignimbrite","Ijolite","Itacolumite","Jadeitite","Jasperoid","Jaspillite","Kenyte",
            "Kimberlite","Komatiite","Lamproite","Lamprophyre","Lapis lazuli","Larvikite","Laterite",
            "Latite","Lherzolite","Lignite","Limestone","Litchfieldite","Litchfieldite","Llanite",
            "Luxullianite","Mangerite","Marble","Marl","Metapelite","Metapsammite","Migmatite",
            "Minette","Monzogranite","Monzonite","Mudstone","Mugearite","Mylonite","Napoleonite",
            "Nepheline syenite","Nephelinite","Norite","Novaculite","Obsidian","Oil shale","Oolite",
            "Pahoehoe","Pantellerite","Pegmatite","Peridotite","Phonolite","Phyllite","Picrite",
            "Pietersite","Porphyry","Pseudotachylite","Pumice","Pyrolite","Pyroxenite",
            "Quartz diorite","Quartz monzonite","Quartzite","Quartzolite","Rapakivi granite",
            "Rhomb porphyry", "Rhyodacite","Rhyolite","Sandstone","Schist","Scoria","Serpentinite",
            "Shale","Shonkinite","Shoshonite","Siltstone","Skarn","Slate","Soapstone","Sovite",
            "Suevite","Syenite","Tachylite","Tachylyte","Taconite","Talc carbonate","Tephrite",
            "Teschenite","Theralite","Tillite","Tonalite","Trachyandesite","Trachyte","Travertine",
            "Troctolite","Trondhjemite","Tufa","Tuff","Turbidite","Unakite","Variolite","Vogesite",
            "Wackestone","Wad","Websterite","Wehrlite","Whiteschist","Adakite","Adamellite",
            "Alkali feldspar granite","Amphibolite","Andesite","Anorthosite","Anthracite","Aphanite",
            "Aplite","Appinite","Argillite","Arkose" ,"Banded iron formation","Basalt",
            "Basaltic  trachyandesite","Basanite","Benmoreite","Blairmorite","Blue Granite",
            "Blueschist","Boninite","Borolanite","Breccia","Calcarenite","Calcflinta","Carbonatite",
            "Cataclasite","Chalk","Charnockite","Chert","Claystone","Coal","Comendite","Conglomerate",
            "Coquina","Dacite","Diabase","Diamictite","Diatomite","Diorite","Dolerite","Dolomite",
            "Dolostone","Dunite","Eclogite","Enderbite","Epidosite","Essexite","Evaporite","Felsite",
            "Flint","Foidolite","Gabbro","Ganister","Geyserite","Gneiss","Gossan","Granite",
            "Granodiorite", "Granophyre","Granulite","Greenschist","Greywacke","Gritstone",
            "Harzburgite","Hawaiite","Hornblendite","Hornfels","Hyaloclastite","Icelandite",
            "Ignimbrite","Ijolite","Itacolumite","Jadeitite","Jasperoid","Jaspillite","Kenyte",
            "Kimberlite","Komatiite","Lamproite","Lamprophyre","Lapislazuli","Larvikite","Laterite",
            "Latite","Lherzolite","Lignite","Limestone","Litchfieldite","Litchfieldite","Llanite",
            "Luxullianite","Mangerite","Marble","Marl","Metapelite","Metapsammite","Migmatite",
            "Minette","Monzogranite","Monzonite","Mudstone","Mugearite","Mylonite","Napoleonite",
            "Nepheline syenite","Nephelinite","Norite","Novaculite","Obsidian","Oil shale","Oolite",
            "Pahoehoe","Pantellerite","Pegmatite","Peridotite","Phonolite","Phyllite","Picrite",
            "Pietersite","Porphyry","Pseudotachylite","Pumice","Pyrolite","Pyroxenite",
            "Quartz Diorite","Quartz monzonite","Quartzite","Quartzolite","Rapakivi granite",
            "Rhombporphyry","Rhyodacite","Rhyolite","Sandstone","Schist","Scoria","Serpentinite",
            "Shale","Shonkinite","Shoshonite","Siltstone","Skarn","Slate","Soapstone","Sovite",
            "Suevite","Syenite","Tachylite","Tachylyte","Taconite","Talc carbonate","Tephrite",
            "Teschenite","Theralite","Tillite","Tonalite","Trachyandesite","Trachyte","Travertine",
            "Troctolite","Trondhjemite","Tufa","Tuff","Turbidite","Unakite","Variolite","Vogesite",
            "Wackestone","Wad" ,"Websterite","Wehrlite","Whiteschist"
    };

    public static String[] mineralComposition = {
            "Andalusite","Anhydrite","Apatite","Arsenopyrite","Augite","Azurite", "Barite","Bauxite",
            "Benitoite","Berylt","Biotite","Bornite","Calcite","Cassiterite","Chalcocite",
            "Chalcopyrite","Chlorite","Chromite","Chrysoberyl","Cinnabar","Clinozoisite","Copper",
            "Cordierite","Corundum","Cuprite","Diamond","Diopside","Dolomite","Enstatite","Epidote",
            "Feldspar","Fluorite","Fuchsite","Galena","Garnet","Glauconite","Gold","Graphite",
            "Gypsum","Halite","Hematite","Hornblende","Ilmenite","Jadeite","Kyanite","Limonite",
            "Magnesite","Magnetite","Malachite","Marcasite","Molybdenite","Monazite","Muscovite",
            "Nepheline","Nephrite","Olivine","Orpiment","Orthoclase","Plagioclase","Prehnite",
            "Pyrite","Pyrophyllite","Pyrrhotite","Quartz","Realgar","Rhodochrosite","Rhodonite",
            "Rutile","Scapolite","Serpentine","Siderite","Sillimanite","Silver","Sodalite",
            "Sphalerite","Spinel","Spodumene","Staurolite","Sulfur","Sylvite","Talc","Titanite",
            "Topaz","Tourmaline","Turquoise","Uraninite","Variscite","Witherite","Wollastonite",
            "Zircon","Zoisite"
    };

    /* Stratigraphy Location */

    public static String[] formationName = {"Tanshauk Member", "Nan-On Formation", "Wunbye Formation",
            "Lokepyin Formation", "Linwe Formation", "Thitsipin Limestone Formation" };

    public static String[] lithology = {"appearance", "bedded", "bedded to massive", "brown", "buff", "calcareous",
            "calcareous Shale", "dark", "dark brown", "dark gray", "Dolomite", "graptolite",
            "graptolite bearing", "graptolite bearing Shale", "gray", "gray to buff", "indurated",
            "interbedded", "irregular", "irregular patches", "lenses", "light", "light to dark gray",
            "Limestone", "Limestone & Dolomite", "Limestone & Wackestone", "massive",
            "medium to thick-bedded", "medium-bedded", "micaceous", "micaceous Siltstone", "Mudstone",
            "Nodular", "nodular appearance", "patches", "Shale", "Siltstone", "soft", "soft to indurated",
            "thick-bedded", "thin to medium-bedded", "thin-bedded", "Wackestone", "yellow", "yellow to buff"
    };

    public static String[] fossil = {"Brachiopoda", "Brachiopods", "Orthis sp", "Paurorthis sp", "Syntrophina sp",
            "Bryozoa", "Fusulina", "Graptolitoidea", "Nautiloidea", "Nautiloids", "Recepticulitid algae",
            "Trilobita" } ;

    public static String[] age = {"Latest Ordovician", "Late Ordovician", "Middle Ordovician", "Early Ordovician"};

    public static String[] mineralization = { "Lead mineral", "Copper mineral", "Gold mineral", "Iron mineral",
            "Antimony mineral", "Tin-Tungsten mineral", "Coal mineral" };

    public static String[] oreName = {"Azurite(2CuCO3Ca(OH)2)", "Barytes(BaSO4)", "Cassiterite(SnO2)",
            "Chalcopyrite(CuFeS2)", "Galena(PbS)", "Graphite(C)", "Hematite(Fe2O3)", "Magnetite(Fe3O4)",
            "Malachite(Cu2CO3)", "Pyrolusite(MnO2)", "Sphalerite(ZnS)", "Stibnite(Sb2S3)",
            "Wolframite((Fe,Mn)WO4)" };

    public static String[] mineralizationNature = { "Layer", "Vein", "Disseminated", "Ore Body", "Aggregated" };
}
