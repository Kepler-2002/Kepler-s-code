package com.sf.minesweeper.listener;

import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.Map;

import javax.swing.JOptionPane;

import com.sf.minesweeper.Client;
import com.sf.minesweeper.bean.GlobalVariable;
import com.sf.minesweeper.bean.MineLabel;
import com.sf.minesweeper.bean.Request;
import com.sf.minesweeper.bean.Response;
import com.sf.minesweeper.dialog.Win;
import com.sf.minesweeper.frame.SartFrame;
import com.sf.minesweeper.tools.Tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.lang.Console;

/**
 * ����������¼�������������Ϸ��ɨ�׵Ķ���
 */
public class MouseListener extends MouseAdapter {
	/**
	 * ��������������,��һ����Ϸ��ʼ
	 */
	private int mousePressedCount=0; // ������Ĵ���
	private int expendedCount; // �׿�չ��������
	// MineField mineField;

	SartFrame sartframe;
	boolean isDouble;
	int temp = Tools.totalMine;// ��ʱ����
	boolean isStart;// ��Ϸ�Ƿ�ʼ

	int score = 0;
	int errornum = 0;

	int flag = 0;

//	///////////////////////////////////
	int i = 1;
//	///////////////////////////////////
	
	int msg = 0;

	public MouseListener(SartFrame sartframe) {
		this.sartframe = sartframe;
	}

	// reset��read��Ӧ�Ĺ��췽��
	// reset:1
	// read:2
	public MouseListener(SartFrame sartframe, int flag) {
		this.sartframe = sartframe;
		this.flag = flag;
	}

	Response response = new Response();
	
	public void mousePressed(MouseEvent arg0) {

		Request request = new Request();
		request.setCommand(Tools.login_in);
		request.setRequestType(3+"");
		request.setUserId(GlobalVariable.userId);
		response = (Response) Client.operate(request);
		
		if (response.getPlayType_in_Game().equals("NONE")) {
			// ����ʹ��ϵͳ
			request.setCommand(Tools.login_in);
			request.setRequestType(0+"");
			request.setUserId(GlobalVariable.userId);
			response = (Response) Client.operate(request);
		}
		
		if (response.getPlayType_in_Game().equals(GlobalVariable.userId)) {
			
			if (i == 1) {
				if (response.getReminder_flagNum()!=-1) {
					temp = response.getReminder_flagNum();
					System.out.println("���Է�������temp:"+temp);
				}
			}
			
			System.out.println("��" + (i++) + "�ε��");

			if (i > 5) {
				// ������Ȩ������һ���û�
				request.setCommand(Tools.login_in);
				request.setRequestType(1+"");
				request.setUserId(GlobalVariable.userId);
				request.setScore(score);
				request.setErrornum(errornum);
				request.setReminder_flagNum(temp);
				response = (Response) Client.operate(request);
				
				System.out.println(response.getScore()+"11111111111111");
				System.out.println(response.getErrornum()+"222222222222");
				
				System.out.println("�����ù⣬�޷���������");
				JOptionPane.showMessageDialog(null, "�����ù⣬�޷���������");
//			sartframe.dispose();
				// ��ǰ�û���������֮�󣬲�����ǰ�û���������
				// ��������Ҫ����ǰ��Ϸ״̬���棬�Ա�����һ���û�������Ϸʱ��ȡ��Ϸ����
				synchronized(MouseListener.class) {
//					System.out.println("��ʼɾ���ļ�");
//					FileUtil.del("D:\\document\\workspace\\MakeMoney_OtherWorkspace\\MineSweeper\\data\\round.txt");
//					System.out.println("��ʼ�����ļ�");
//					FileUtil.touch("D:\\document\\workspace\\MakeMoney_OtherWorkspace\\MineSweeper\\data\\round.txt");
					System.out.println("��ʼ�浵");
					sartframe.save("../data/round.txt");
					System.out.println("�浵���");
					//��������Ҫ֪ͨ���е��û����������Ϸ����
					FileWriter writer = new FileWriter("../data/msg.txt");
					writer.write((msg++)+"");
//					sartframe.read("../data/round.txt");
				}

				return;
			}
			System.out.println("��" + (i - 1) + "�ε��");

			// ��ȡ������Ķ���
			MineLabel mineLabel = (MineLabel) arg0.getSource();
			int d = arg0.getModifiersEx(); // ���ش��¼������η�����
			int d1 = arg0.getModifiers();
			// ��ȡ��ǰ������׿������������
			// �� �� ����0��ʼ
			int rowIndex = mineLabel.getRowIndex();
			int colIndex = mineLabel.getColIndex();
			
			
			
			// ��¼�����������
			mousePressedCount++;

			// ����ǵ�һ�ε����������ʼ����
			if (mousePressedCount == 1) {
				sartframe.getMineField().buildMine(rowIndex, colIndex, flag);
			}

			mineLabel = sartframe.getMineField().getMineLabel()[rowIndex][colIndex];
			System.out.println(mineLabel.getRowIndex());
			System.out.println(mineLabel.getColIndex());
			System.out.println(mineLabel.isMine());
			
			
			// ���ǵ�һ�ε������ʱ�����Ѿ��������
			// ��Ҫ��һ��ȷ�����ĵ���¼��� ������Ҽ���˫��
			// .............................1˫������.............................
			if (d == InputEvent.BUTTON1_DOWN_MASK + InputEvent.BUTTON3_DOWN_MASK) {
				// 1 ��ΧͼƬ�л�
				// �������е��׿�
				for (int x = Math.max(rowIndex - 1, 0); x <= Math.min(rowIndex + 1, Tools.totalx - 1); x++) {
					for (int y = Math.max(colIndex - 1, 0); y <= Math.min(colIndex + 1, Tools.totaly - 1); y++) {
						mineLabel = sartframe.getMineField().getMineLabel()[x][y];
						if (!mineLabel.isExpanded() && !mineLabel.isFlag())
							mineLabel.setIcon(Tools.mineCount[0]);
					}
				}
				// 2 ����侪��
				sartframe.getMineState().getNewGame().setIcon(Tools.iiface2);
				// 3 ��¼˫�������־
				isDouble = true;

				// ..............................2�Ҽ�����
				// ���׿�δչ��:��������....................................
			} else if (d1 == InputEvent.BUTTON3_MASK && !mineLabel.isExpanded()) {
				// �Ҽ��������
				int clickCount = mineLabel.getRightClickCount();
				clickCount++;
				// ��һ�Σ��׿�>>>����
				if (clickCount == 1) {
					mineLabel.setIcon(Tools.iiflag);
					mineLabel.setRightClickCount(clickCount);
					mineLabel.setFlag(true);

					// �Ƴ�����
					sartframe.getMineField().getMineLabel()[rowIndex][colIndex]
							.removeMouseListener(sartframe.getMineField().getMouseListener());

					if (mineLabel.isMine()) {
						// ��ǰ��ʣ������
						temp--;
						System.out.println("temp="+temp);
						// �ܷ�+1
						score++;
						System.out.println("���ף������ȷ");
						// �ܷ�+1
						sartframe.getMineState().setTotalMineG(score);
						
						isMind();
						
					} else {
						errornum++;
						System.out.println("�����ף���Ǵ���");
						sartframe.getMineState().setErrorAction(errornum);
						// Ȼ�󽫱�ǵĵط��ķ����ͼ��չʾ����
						mineLabel.setIcon(Tools.mineCount[mineLabel.getMineCount()]);
					}

				}

				// �ڶ��Σ�����>>>�ʺ�
				if (clickCount == 2) {
					mineLabel.setIcon(Tools.iiask0);
					mineLabel.setRightClickCount(clickCount);
					mineLabel.setFlag(false);
					if (mineLabel.isMine()) {
						// ��ǰ��ʣ������
						temp++;
						// �ܷ�-1
						score--;
						sartframe.getMineState().setTotalMineG(score);
					} else {
						// �ָ�
						errornum--;
						sartframe.getMineState().setErrorAction(errornum);
					}

				}
				// �����Σ��ʺ�>>>�׿�
				if (clickCount == 3) {
					mineLabel.setIcon(Tools.iiblank);
					mineLabel.setRightClickCount(0);
				}
				// **************************3�������************************************
				// ������
			} else if (d1 == InputEvent.BUTTON1_MASK) {
				// δչ���Ҳ�������(ͼƬ�ݽ�ȥ)
				if (!mineLabel.isExpanded() && !mineLabel.isFlag()) {
					// �ʺ�
					if (mineLabel.getRightClickCount() == 2) {
						mineLabel.setIcon(Tools.iiask1);
						// �׿�
					} else {
						mineLabel.setIcon(Tools.mineCount[0]);
					}
					// ����>>����
					sartframe.getMineState().getNewGame().setIcon(Tools.iiface2);
				}

				if (mineLabel.isExpanded()) {
					sartframe.getMineState().getNewGame().setIcon(Tools.iiface0);
				}

			}
		}else {
			JOptionPane.showMessageDialog(null, "�����������������Ϸ�У����޷�����");
		}

	}

	/**
	 * ����ͷ�
	 */
	public void mouseReleased(MouseEvent arg0) {

		MineLabel mineLabel = (MineLabel) arg0.getSource();

		// ��ȡ������׿������
		int rowIndex = mineLabel.getRowIndex();
		int colIndex = mineLabel.getColIndex();

		int i = arg0.getModifiers();

		// ***************˫���ͷ�*********************************
		if (isDouble) {
			// 2 ���龪�Ȼ�ԭ
			sartframe.getMineState().getNewGame().setIcon(Tools.iiface0);
			doubleReleased(rowIndex, colIndex, mineLabel.getMineCount());
			isDouble = false;
			isMind();

			// ***************����ͷ�********************************
		} else if (i == InputEvent.BUTTON1_MASK) {
			if (!mineLabel.isExpanded() && !mineLabel.isFlag()) {
				/**
				 * ����һ�ε������ʱ��ʱ����ʼ��ʱ
				 */

				if (!sartframe.isStart()) {
//					sartframe.getTimer().start();
					sartframe.setStart(true); // ��Ϸ��ʼ
				}
				// �ȵ���,��ǰ����-1
				if (mineLabel.isMine() && !mineLabel.isFlag()) {
					mineLabel.setIcon(Tools.iiblood);
//					openMine(rowIndex, colIndex);
					score--;
					sartframe.getMineState().setTotalMineG(score);
					// Ȼ�󽫱�ǵĵط��ķ����ͼ��չʾ����
					//mineLabel.setIcon(Tools.mineCount[mineLabel.getMineCount()]);
					// �Ƴ�����
					sartframe.getMineField().getMineLabel()[rowIndex][colIndex]
							.removeMouseListener(sartframe.getMineField().getMouseListener());

					sartframe.getMineState().getNewGame().setIcon(Tools.iiface3);

//					sartframe.getTimer().stop();
//					sartframe.setStart(false);

					// û�вȵ���,չ����ȫ����
				} else {
					score++;
					sartframe.getMineState().setTotalMineG(score);
					sartframe.getMineState().getNewGame().setIcon(Tools.iiface0);
					open(rowIndex, colIndex);
					isMind();
				}
			} else if (mineLabel.isExpanded()) {
				sartframe.getMineState().getNewGame().setIcon(Tools.iiface0);
			}
		}
	}

	public void doubleReleased(int rowIndex, int colIndex, int count) {
		/**
		 * flagBeside ��Χ8��λ�õ�������
		 * 
		 */
		int flagBeside = 0;
		for (int x = Math.max(rowIndex - 1, 0); x <= Math.min(rowIndex + 1, Tools.totalx - 1); x++) {
			for (int y = Math.max(colIndex - 1, 0); y <= Math.min(colIndex + 1, Tools.totaly - 1); y++) {
				if (sartframe.getMineField().getMineLabel()[x][y].isFlag()) {
					flagBeside++;
				}

			}
		}

		// 1 ��ǰ�׿�δչ�����������������ֲ���ȣ�ͼƬ��ԭ
		if (!sartframe.getMineField().getMineLabel()[rowIndex][colIndex].isExpanded()
				|| sartframe.getMineField().getMineLabel()[rowIndex][colIndex].getMineCount() != flagBeside) {
			doublePressedBeside(rowIndex, colIndex, 2);
		}

		// 2 �Ѿ�չ������Χ������������ȣ��ж���Χ8�������Ƿ��вȵ���(���ף���û������)
		if (sartframe.getMineField().getMineLabel()[rowIndex][colIndex].isExpanded()
				&& sartframe.getMineField().getMineLabel()[rowIndex][colIndex].getMineCount() == flagBeside) {
			boolean isBobm = false;
			for (int x = Math.max(rowIndex - 1, 0); x <= Math.min(rowIndex + 1, Tools.totalx - 1); x++) {
				for (int y = Math.max(colIndex - 1, 0); y <= Math.min(colIndex + 1, Tools.totaly - 1); y++) {
					if (sartframe.getMineField().getMineLabel()[x][y].isMine()
							&& !sartframe.getMineField().getMineLabel()[x][y].isFlag()) {
						isBobm = true;
						break;
					} else if (!sartframe.getMineField().getMineLabel()[x][y].isExpanded()) {
						open(x, y);
					}
				}
			}
			// �ȵ���
			if (isBobm) {
				openMine(rowIndex, colIndex);
			}

		}

	}

	public void doublePressedBeside(int rowIndex, int colIndex, int doubleType) {

		doublePressed(rowIndex, colIndex, doubleType);

		for (int x = Math.max(rowIndex - 1, 0); x <= Math.min(rowIndex + 1, Tools.totalx - 1); x++) {
			for (int y = Math.max(colIndex - 1, 0); y <= Math.min(colIndex + 1, Tools.totaly - 1); y++) {
				doublePressed(x, y, doubleType);
			}
		}

	}

	public void doublePressed(int i, int j, int doubleType) {
		MineLabel minelabel = sartframe.getMineField().getMineLabel()[i][j];

		if (!minelabel.isExpanded() && !minelabel.isFlag()) {

			if (doubleType == 1) {

				if (minelabel.getRightClickCount() == 2) {

					minelabel.setIcon(Tools.iiask1);

				} else {
					minelabel.setIcon(Tools.mineCount[0]);

				}
			} else {
				if (minelabel.getRightClickCount() == 2) {
					minelabel.setIcon(Tools.iiask0);
				} else {

					minelabel.setIcon(Tools.iiblank);

				}
			}
		}
	}

	/**
	 * �����е�����չʾ����

	 */
	public void openMine(int i, int j) {
		for (int m = 0; m < Tools.totalx; m++) {
			for (int n = 0; n < Tools.totaly; n++) {
				MineLabel mineLabel = sartframe.getMineField().getMineLabel()[m][n];
				// ���׵���� �Ҳ�������
				if (mineLabel.isMine() && !mineLabel.isFlag()) {
					// ���׵�������
					if (i == m && j == n) {
						mineLabel.setIcon(Tools.iiblood);
						// ������������չʾ
					} else {
						mineLabel.setIcon(Tools.iimine);
					}

					// �����ף�������
				} else if (!mineLabel.isMine() && mineLabel.isFlag()) {
					mineLabel.setIcon(Tools.iierror);
					sartframe.getMineState().getNewGame().setIcon(Tools.iiface3);
					sartframe.getTimer().stop();
					sartframe.setStart(false);

				}
				// �Ƴ����е�����¼�
				mineLabel.removeMouseListener(sartframe.getMineField().getMouseListener());
			}
		}
	}

	/**
	 * ���û�д��ף�չ����ǰ����
	 */
	private void open(int rowIndex, int colIndex) {

		MineLabel mineLabel = sartframe.getMineField().getMineLabel()[rowIndex][colIndex];
		// û�б�չ���ģ���û�б���Ϊ����ķ���
		if (!mineLabel.isExpanded() && !mineLabel.isFlag()) {
			// ��ȡ�����������׿�����
			int count = mineLabel.getMineCount();

			mineLabel.setIcon(Tools.mineCount[count]);

			mineLabel.setExpanded(true);
			expendedCount++;

			// �����ǰ������Χ����Ϊ0������Χ����ȫ��չ��
			if (count == 0) {
				for (int x = Math.max(rowIndex - 1, 0); x <= Math.min(rowIndex + 1, Tools.totalx - 1); x++) {
					for (int y = Math.max(colIndex - 1, 0); y <= Math.min(colIndex + 1, Tools.totaly - 1); y++) {
						open(x, y);
					}
				}
			}
		}

	}

	/**
	 * �ж����е���ʱ���Ź���
	 */
	public void isMind() {
		
		System.out.println("ʣ���������"+temp);
		
//		��������ʼ������Ϸ�������ҽ�������
		Request request = new Request();
		request.setCommand(Tools.login_in);
		//��ȡ����Ϸ������ص���Ϣ
		request.setRequestType(4+"");
		request.setUserId(GlobalVariable.userId);
		response = (Response) Client.operate(request);
		
		Map<String, Integer> map = response.getScoreMap();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			System.out.println("�û�id:"+entry.getKey()+"\t�û��ܷ�:"+entry.getValue());
		}
		Map<String, Integer> map1 = response.getErrornumMap();
		for (Map.Entry<String, Integer> entry : map1.entrySet()) {
			System.out.println("�û�id:"+entry.getKey()+"\t�û�ʧ�����:"+entry.getValue());
		}
		
		//���˫���ķ�����������Ϸ����δ��������������ֱ���ж����Ʒ���ʤ��
		//��ȡ��ǰ�û��ĵ�ǰ�÷�
		Integer localscole_temp = map.get(GlobalVariable.userId);
		//��ȡ��һ���û��ĵ÷�
		Integer otherscole_temp = null;
		//��ǰ�û���ʧ����
		Integer localErrorNum_temp = map1.get(GlobalVariable.userId);
		//��һ���û���ʧ����
		Integer otherErrorNum_temp = null;
		//��һ����ҵ�id
		String otherId = null;
		
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (!entry.getKey().equals(GlobalVariable.userId)) {
				otherId = entry.getKey();
				otherscole_temp = entry.getValue();
			}
		}
		
		for (Map.Entry<String, Integer> entry : map1.entrySet()) {
			if (!entry.getKey().equals(GlobalVariable.userId)) {
				otherErrorNum_temp = entry.getValue();
			}
		}
		
		//��ʤ�ߵ��û�(Ĭ��Ϊƽ��)
		String winnerId = "ƽ��";
		
		//ת��Ϊint��
		int localscore = localscole_temp;
		int otherscore = otherscole_temp;
		int localErrorNum = localErrorNum_temp;
		int otherErrorNum = otherErrorNum_temp;
		//�����ֵ
		int d_value = Math.abs(localscore-otherscore);
		//��ǰ��ֵ����ʣ����������������
		if (d_value > temp) {
			// �������еķ���
			for (int g = 0; g < Tools.totalx; g++)
				for (int h = 0; h < Tools.totaly; h++) {
					// �����ǰ�������׿���û�б���עΪ����
					if (sartframe.getMineField().getMineLabel()[g][h].isMine()
							&& !sartframe.getMineField().getMineLabel()[g][h].isFlag()) {
						// ����ǰ�����עΪ����
						sartframe.getMineField().getMineLabel()[g][h].setIcon(Tools.iiflag);
					}
					// �Ƴ�����
					sartframe.getMineField().getMineLabel()[g][h]
							.removeMouseListener(sartframe.getMineField().getMouseListener());

				}
			sartframe.getMineState().getNewGame().setIcon(Tools.iiface4);

			sartframe.getMineState().setTotalMineG(0);

			sartframe.getTimer().stop();
			
			winnerId = localscore>otherscore ? GlobalVariable.userId:otherId;
			// ������ʤ����
			
			//��Ϸ����֮ǰ��������ԭ��
			JOptionPane.showMessageDialog(null, "��Ϸ��������Ϊ������ȣ�ʧ����ȣ�ƽ��");
			//"��Ϸ��������Ϊ������ȣ�ʧ���ٵ�һ����ʤ"
			//"��Ϸ��������Ϊ��ǰ�������ߵ���Ϸ��ҵıȷֲ�ֵ����ʣ������"
			new Win(sartframe,winnerId);

			// �ɹ��󵯳�Ӣ�ۼ�¼��
			sartframe.setStart(false);
		}
		
		// TODO Auto-generated method stubt;
		//�����׽�����
		if (temp == 0) {

			System.out.println("���е��׶��Ź��ˣ�����");
			
			//1.�������߻�ʤ
			if (localscore!=otherscore) {
				winnerId = localscore>otherscore ? GlobalVariable.userId:otherId;
				
				// �������еķ���
				for (int g = 0; g < Tools.totalx; g++)
					for (int h = 0; h < Tools.totaly; h++) {
						// �����ǰ�������׿���û�б���עΪ����
						if (sartframe.getMineField().getMineLabel()[g][h].isMine()
								&& !sartframe.getMineField().getMineLabel()[g][h].isFlag()) {
							// ����ǰ�����עΪ����
							sartframe.getMineField().getMineLabel()[g][h].setIcon(Tools.iiflag);
						}
						// �Ƴ�����
						sartframe.getMineField().getMineLabel()[g][h]
								.removeMouseListener(sartframe.getMineField().getMouseListener());

					}
				sartframe.getMineState().getNewGame().setIcon(Tools.iiface4);

				sartframe.getMineState().setTotalMineG(0);

				sartframe.getTimer().stop();
				
				// ������ʤ����
				JOptionPane.showMessageDialog(null, "��Ϸ��������Ϊ�������߻�ʤ");
				new Win(sartframe,winnerId);
			}
			//2.������ȣ�ʧ���ٵ�һ����ʤ��
			else if(localErrorNum != otherErrorNum){
				winnerId = localErrorNum>otherErrorNum ? GlobalVariable.userId:otherId;
				// �������еķ���
				for (int g = 0; g < Tools.totalx; g++)
					for (int h = 0; h < Tools.totaly; h++) {
						// �����ǰ�������׿���û�б���עΪ����
						if (sartframe.getMineField().getMineLabel()[g][h].isMine()
								&& !sartframe.getMineField().getMineLabel()[g][h].isFlag()) {
							// ����ǰ�����עΪ����
							sartframe.getMineField().getMineLabel()[g][h].setIcon(Tools.iiflag);
						}
						// �Ƴ�����
						sartframe.getMineField().getMineLabel()[g][h]
								.removeMouseListener(sartframe.getMineField().getMouseListener());

					}
				sartframe.getMineState().getNewGame().setIcon(Tools.iiface4);

				sartframe.getMineState().setTotalMineG(0);

				sartframe.getTimer().stop();
				
				// ������ʤ����
				JOptionPane.showMessageDialog(null, "��Ϸ��������Ϊ������ȣ�ʧ���ٵ�һ����ʤ");
				new Win(sartframe,winnerId);
			}
			
			// �ɹ��󵯳�Ӣ�ۼ�¼��
			sartframe.setStart(false);
		}

	}

}
