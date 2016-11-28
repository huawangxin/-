package com.huawangxin.aircraft;

import java.awt.Image;

import org.loon.framework.javase.game.action.sprite.Animation;
import org.loon.framework.javase.game.action.sprite.Sprite;
import org.loon.framework.javase.game.action.sprite.SpriteImage;
import org.loon.framework.javase.game.core.geom.RectBox;
import org.loon.framework.javase.game.utils.GraphicsUtils;

/**
 * �����������࣬�̳���LGame��Sprite�࣬������·����� 1. ������ʾ����ʱ�����ķ��� 2. ���ڼ����ײ�ķ���
 * 
 */
public class AircraftSprite extends Sprite {

	private static final long serialVersionUID = 1L;
	// �Ƿ����ٱ��
	private boolean destroy = false;
	// ����ʱ�Ƿ�Ҫ��ʾ�������
	private boolean showDestroyAnimation = false;
	// ����ʱ��������
	private Animation destroyAnimation;
	// ����ʱ��ʾ�����ĵ�ǰ֡
	private int currentDestroyFrame;
	// ����ʱ��ʾ������֡��
	private int destroyFrameNum;

	/**
	 * ����ʱ��ʾ�����е�һ֡������Ѿ���ʾ��ȫ��֡����true�����򷵻�false
	 * 
	 * @return �Ƿ��Ѿ���ʾ������֡�������ٶ����Ƿ��Ѿ��������
	 */
	public boolean showDestroyFrame() {
		if (showDestroyAnimation) {
			this.setRunning(false);
			setAnimation(destroyAnimation);
			if (currentDestroyFrame < destroyFrameNum) {
				this.setCurrentFrameIndex(currentDestroyFrame);
			}
			return ++currentDestroyFrame > destroyFrameNum;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param img
	 * @param destroyImg
	 * @param w
	 * @param h
	 */
	public AircraftSprite(String img, String destroyImg, int w, int h) {
		super(img, w, h);
		// ���ض�����ͼƬ
		Image[] images = GraphicsUtils.getSplitImages(destroyImg, w, h);

		destroyFrameNum = images.length;

		currentDestroyFrame = 0;
		destroyAnimation = new Animation();
		for (int i = 0; i < images.length; i++) {
			// ���øö�����ÿһ֡
			destroyAnimation.addFrame(new SpriteImage(images[i]), 200);
		}
	}

	/**
	 * 
	 * @param img
	 */
	public AircraftSprite(String img) {
		super(img);
	}

	/**
	 * 
	 * @param img
	 * @param w
	 * @param h
	 */
	public AircraftSprite(String img, int w, int h) {
		super(img, w, h);
	}

	/**
	 * �����ĳ��Sprite�Ƿ���ײ
	 * 
	 * @param sprite
	 * @return
	 */
	public boolean checkCollision(AircraftSprite sprite) {
		if (rect == null) {
			rect = new RectBox((int) this.getX(), (int) this.getY(),
					getWidth(), getHeight());
		} else {
			rect.setBounds((int) this.getX(), (int) this.getY(), getWidth(),
					getHeight());
		}
		return rect.intersects((int) sprite.getX(), (int) sprite.getY(),
				sprite.getWidth(), sprite.getHeight());
	}

	public boolean isDestroy() {
		return destroy;
	}

	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}

	public boolean isShowDestroyAnimation() {
		return showDestroyAnimation;
	}

	public void setShowDestroyAnimation(boolean showDestroyAnimation) {
		this.showDestroyAnimation = showDestroyAnimation;
	}
	

}
