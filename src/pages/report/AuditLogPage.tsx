import { Card, DatePicker, Input, Select, Space, Table, Tag, Typography } from 'antd'
import { useState } from 'react'
import { SearchOutlined, UserOutlined } from '@ant-design/icons'
import dayjs from 'dayjs'
import PageHeader from '@/components/shared/PageHeader.tsx'
import { useGetAuditLogsQuery } from '@/features/auditLog/auditLogApi.ts'

const { Text } = Typography
const { RangePicker } = DatePicker

const ACTION_COLORS: Record<string, string> = {
  IMPORT_STOCK: 'blue',
  CREATE_ORDER: 'green',
  CANCEL_ORDER: 'red',
  MARK_DAMAGED: 'volcano',
  ADJUST_STOCK: 'purple',
  RETURN_ORDER: 'orange'
}

const STATUS_COLORS: Record<string, string> = {
  SUCCESS: 'green',
  FAILED: 'red',
  DENIED: 'volcano',
  UNAUTHORIZED: 'purple',
  EXPIRED: 'orange'
}

const AuditLogPage = () => {
  const [filters, setFilters] = useState({
    page: 0,
    size: 50,
    action: undefined,
    entity: undefined,
    status: undefined
  })
  const { data: response, isLoading, isFetching } = useGetAuditLogsQuery(filters)

  const columns = [
    {
      title: 'Thời gian',
      dataIndex: 'createdAt',
      key: 'createdAt',
      width: 180,
      render: (v: string) => <Text type='secondary'>{v ? dayjs(v).format('DD/MM/YYYY HH:mm:ss') : '-'}</Text>
    },
    {
      title: 'Người thực hiện',
      dataIndex: 'actorName',
      key: 'actor',
      width: 180,
      render: (v: string) => (
        <Space>
          <UserOutlined style={{ fontSize: 12 }} />
          <Text strong>{v || 'Hệ thống'}</Text>
        </Space>
      )
    },
    {
      title: 'Hành động',
      dataIndex: 'action',
      key: 'action',
      width: 180,
      render: (v: string) => <Tag color={ACTION_COLORS[v] || 'default'}>{v}</Tag>
    },
    {
      title: 'Đối tượng',
      dataIndex: 'entityName',
      key: 'entity',
      width: 180,
      render: (v: string) => <Tag>{v}</Tag>
    },
    {
      title: 'ID Tham chiếu',
      dataIndex: 'entityId',
      key: 'ref',
      width: 180,
      render: (v: string) => <Text code>{v}</Text>
    },
    {
      title: 'Trạng thái',
      dataIndex: 'status',
      key: 'status',
      width: 110,
      render: (v: string) => <Tag color={STATUS_COLORS[v] || 'default'}>{v}</Tag>
    },
    {
      title: 'Nội dung chi tiết',
      dataIndex: 'details',
      key: 'message',
      render: (v: string) => <Text italic>{v}</Text>
    }
  ]
  return (
    <div>
      <PageHeader
        title='Nhật ký hệ thống'
        subtitle='Theo dõi toàn bộ biến động dữ liệu và lịch sử thao tác của người dùng'
      />

      <Card style={{ marginBottom: 16, borderRadius: 12 }}>
        <Space wrap size={16}>
          <div>
            <div style={{ marginBottom: 4 }}>
              <Text type='secondary'>Hành động</Text>
            </div>
            <Select
              allowClear
              placeholder='Tất cả hành động'
              style={{ width: 180 }}
              options={[
                { value: 'IMPORT_STOCK', label: 'Nhập kho' },
                { value: 'CREATE_ORDER', label: 'Tạo đơn hàng' },
                { value: 'CANCEL_ORDER', label: 'Huỷ đơn' },
                { value: 'MARK_DAMAGED', label: 'Báo hỏng' }
              ]}
              onChange={(v) => setFilters({ ...filters, action: v })}
            />
          </div>

          <div>
            <div style={{ marginBottom: 4 }}>
              <Text type='secondary'>Thời gian</Text>
            </div>
            <RangePicker showTime />
          </div>

          <div>
            <div style={{ marginBottom: 4 }}>
              <Text type='secondary'>Tìm kiếm</Text>
            </div>
            <Input prefix={<SearchOutlined />} placeholder='Tìm ID, nội dung...' style={{ width: 250 }} />
          </div>
        </Space>
      </Card>

      <Card style={{ borderRadius: 12, boxShadow: '0 2px 10px rgba(0,0,0,0.07)' }}>
        <Table
          dataSource={response}
          columns={columns}
          loading={isLoading || isFetching}
          rowKey='id'
          size='middle'
          pagination={{
            pageSize: filters.size,
            current: filters.page + 1,
            onChange: (p, s) => setFilters({ ...filters, page: p - 1, size: s }),
            showSizeChanger: true,
            showTotal: (total) => `Tổng cộng ${total} bản ghi`
          }}
        />
      </Card>
    </div>
  )
}

export default AuditLogPage
