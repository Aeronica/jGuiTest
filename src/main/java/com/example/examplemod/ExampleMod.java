package com.example.examplemod;

import com.example.examplemod.gui.GuiHandler;
import com.example.examplemod.gui.GuiSimple;
import com.example.examplemod.gui.GuiSimple2;
import com.example.examplemod.gui.GuiSimple3;
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

import javax.annotation.Nonnull;

import static com.example.examplemod.Reference.*;

@Mod.EventBusSubscriber
@Mod(
        modid = MOD_ID, name = MOD_NAME,
        version = VERSION, acceptedMinecraftVersions = ACCEPTED_MINECRAFT_VERSIONS,
        dependencies = DEPENDENTS
)
public class ExampleMod
{
    private static final TheItem TEST_ITEM = new TheItem();

    public static Logger logger;

    @Mod.Instance(MOD_ID)
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

    @Mod.EventBusSubscriber(modid = MOD_ID, value = Side.CLIENT)
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
                openGui3();
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
        new GuiSimple2(null).display();
    }

    @SideOnly(Side.CLIENT)
    private static void openGui3()
    {
        new GuiSimple3(null).display();
    }

    public static class TheItem extends Item
    {

        TheItem()
        {
            setMaxStackSize(16);
            setRegistryName("the_item");
            setTranslationKey(getRegistryName().toString());
            setCreativeTab(CreativeTabs.MISC);
            hasSubtypes = false;
        }

        @Nonnull
        @Override
        public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn)
        {
            if (worldIn.isRemote)
            {
                logger.info("RightClicked");
                return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
            }
            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
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
