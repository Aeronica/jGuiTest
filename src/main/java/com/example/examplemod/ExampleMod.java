package com.example.examplemod;

import com.example.examplemod.gui.GuiHandler;
import com.example.examplemod.gui.GuiSimple;
import com.example.examplemod.gui.GuiSimple2;
import de.johni0702.minecraft.gui.container.GuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
@Mod(modid = ExampleMod.MODID, name = ExampleMod.NAME, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "examplemod";
    public static final String NAME = "Example Mod";
    public static final String VERSION = "1.0";

    public static final TheItem TEST_ITEM = new TheItem();

    public static Logger logger;

    @Mod.Instance(MODID)
    public static ExampleMod instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        new GuiHandler(this).register();
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        logger.info("RegisterItems");
        event.getRegistry().register(TEST_ITEM);
    }

    @Mod.EventBusSubscriber(modid = MODID, value = Side.CLIENT)
    public static class ClientEventHandler
    {
        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event)
        {
            logger.info("registerModels");
            ModelLoader.setCustomModelResourceLocation(TEST_ITEM, 0, new ModelResourceLocation(TEST_ITEM.getRegistryName().toString(), "inventory"));
        }

        @SubscribeEvent
        public static void event(PlayerInteractEvent.RightClickItem event)
        {
            if (event.getItemStack().getItem() instanceof TheItem)
            {
                openGui2();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private static void openGui()
    {
        new GuiSimple(GuiScreen.wrap(Minecraft.getMinecraft().currentScreen), instance).display();
    }

    @SideOnly(Side.CLIENT)
    private static void openGui2()
    {
        new GuiSimple2(Minecraft.getMinecraft().currentScreen).display();
    }

    public static class TheItem extends Item
    {

        public TheItem()
        {
            setMaxStackSize(16);
            setRegistryName("the_item");
            setTranslationKey(getRegistryName().toString());
            setCreativeTab(CreativeTabs.MISC);
            hasSubtypes = false;
        }

        @Override
        public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
        {
            if (worldIn.isRemote)
            {
                logger.info("RightClicked");
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
            }
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }

        @Override
        public boolean isDamageable()
        {
            return false;
        }

        @Override
        public boolean getShareTag()
        {
            return true;
        }

        @Override
        public int getMaxItemUseDuration(ItemStack stack)
        {
            return 75;
        }
    }
}
