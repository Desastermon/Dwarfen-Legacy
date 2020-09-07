/*******************************************************************************
 * Copyright (c) 2020 Nick Pardun.
 *  This program and the accompanying materials
 * are made available under the terms of the GNU General Public License v3 (GPLv3)
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * SPDX-License-Identifier: GPL-3.0-only
 *******************************************************************************/
package desastermon.dwarfen_legacy.blocks;

import java.util.Random;

import desastermon.dwarfen_legacy.blocks.entity.CustomFurnaceBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CustomFurnace extends Block implements BlockEntityProvider{

	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	public static final BooleanProperty LIT = Properties.LIT;

	public CustomFurnace(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(LIT, false));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return new CustomFurnaceBlockEntity();
	}

	public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
		super.onSyncedBlockEvent(state, world, pos, type, data);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity == null ? false : blockEntity.onSyncedBlockEvent(type, data);
	}

	public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory)blockEntity : null;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
			if (screenHandlerFactory != null) {
				player.openHandledScreen(screenHandlerFactory);
			}
		}
		return ActionResult.SUCCESS;
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof CustomFurnaceBlockEntity) {
				ItemScatterer.spawn(world, pos, (CustomFurnaceBlockEntity)blockEntity);
			}
			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}

	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
	}

	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, LIT);
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if(state.get(LIT)) {
			double x = pos.getX() + 0.5D;
			double y = pos.getY();
			double z = pos.getZ() + 0.5D;
			if(random.nextDouble() < 0.15D) {
				world.playSound(x, y, z, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1, 1, false);
			}
			Direction dir = state.get(FACING);
			Direction.Axis axis = dir.getAxis();
			double d1 = random.nextDouble() * 0.6D - 0.3D;
			double d2 = axis == Direction.Axis.X ? dir.getOffsetX() * 0.52D : d1;
			double d3 = random.nextDouble() * 6D / 16D;
			double d4 = axis == Direction.Axis.Z ? dir.getOffsetZ() * 0.52D : d1;
			if(random.nextDouble() < 0.1D) {
				world.addParticle(ParticleTypes.SMOKE, x + d2, y + d3, z + d4, 0, 0, 0);
				world.addParticle(ParticleTypes.FLAME, x + d2, y + d3, z + d4, 0, 0, 0);
			}
			d1 = random.nextDouble() * 0.4D - 0.2D;
			if(random.nextDouble() < 0.15D) {
				world.addParticle(ParticleTypes.FLAME, x + d1, y + 1.1D, z + d1, 0, 0, 0);
			}
			world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x + d1, y + 1, z + d1, 0, random.nextDouble() * 0.05 + 0.05D, 0);
		}
		super.randomDisplayTick(state, world, pos, random);
	}


}
